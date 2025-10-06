// Dashboard utilities and enhanced functionality
class ApiCostDashboard {
    constructor() {
        this.apiData = {};
        this.filteredData = {};
        this.currentFilter = 'all';
        this.sortColumn = 'productId';
        this.sortDirection = 'asc';
    }

    async initialize() {
        this.setupEventListeners();
        await this.loadData();
        this.renderFilters();
    }

    setupEventListeners() {
        // Refresh button
        document.getElementById('refreshBtn').addEventListener('click', () => this.loadData());
        
        // Export button  
        document.getElementById('exportBtn').addEventListener('click', () => this.exportData());
        
        // Search functionality
        const searchInput = document.getElementById('searchInput');
        if (searchInput) {
            searchInput.addEventListener('input', (e) => this.handleSearch(e.target.value));
        }
    }

    async loadData() {
        this.setStatus('Loading data...', 'loading');

        try {
            const response = await fetch('/api/cost-breakdown/api-to-product-report');
            
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            this.apiData = await response.json();
            this.filteredData = { ...this.apiData };
            this.renderDashboard();
            this.setStatus('Data loaded successfully', 'success');
        } catch (error) {
            console.error('Error loading data:', error);
            this.setStatus('Error loading data: ' + error.message, 'error');
            this.showEmptyState();
        }
    }

    setStatus(message, type) {
        const statusElement = document.getElementById('status');
        statusElement.textContent = message;
        statusElement.className = 'status ' + type;
    }

    renderDashboard() {
        if (Object.keys(this.apiData).length === 0) {
            this.showEmptyState();
            return;
        }

        this.hideEmptyState();
        this.renderSummaryCards();
        this.renderDetailedTable();
        this.renderCharts();
    }

    renderSummaryCards() {
        const summaryContainer = document.getElementById('summaryCards');
        const stats = this.calculateStats();

        summaryContainer.innerHTML = `
            <div class="summary-card">
                <h3>${stats.apiCount}</h3>
                <p>Total APIs</p>
            </div>
            <div class="summary-card">
                <h3>${stats.productCount}</h3>
                <p>Unique Products</p>
            </div>
            <div class="summary-card">
                <h3>${stats.totalEntries}</h3>
                <p>Product-API Relationships</p>
            </div>
            <div class="summary-card">
                <h3>$${stats.totalTco.toLocaleString()}</h3>
                <p>Total TCO</p>
            </div>
        `;
    }

    calculateStats() {
        const apiCount = Object.keys(this.filteredData).length;
        let totalEntries = 0;
        let totalTco = 0;
        const products = new Set();

        Object.values(this.filteredData).forEach(entries => {
            totalEntries += entries.length;
            entries.forEach(entry => {
                totalTco += entry.tco || 0;
                products.add(entry.productId);
            });
        });

        return {
            apiCount,
            productCount: products.size,
            totalEntries,
            totalTco
        };
    }

    renderDetailedTable() {
        const container = document.getElementById('detailedTable');
        if (!container) return;

        // Flatten data for table view
        const flatData = [];
        Object.entries(this.filteredData).forEach(([apiName, entries]) => {
            entries.forEach(entry => {
                flatData.push({ ...entry, apiName });
            });
        });

        // Sort data
        flatData.sort((a, b) => {
            const aVal = a[this.sortColumn] || '';
            const bVal = b[this.sortColumn] || '';
            
            if (this.sortDirection === 'asc') {
                return aVal.toString().localeCompare(bVal.toString());
            } else {
                return bVal.toString().localeCompare(aVal.toString());
            }
        });

        container.innerHTML = `
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th onclick="dashboard.sortBy('apiName')">API Name ${this.getSortIcon('apiName')}</th>
                            <th onclick="dashboard.sortBy('productId')">Product ID ${this.getSortIcon('productId')}</th>
                            <th onclick="dashboard.sortBy('fundingStrategy')">Funding Strategy ${this.getSortIcon('fundingStrategy')}</th>
                            <th onclick="dashboard.sortBy('tco')">TCO ${this.getSortIcon('tco')}</th>
                            <th onclick="dashboard.sortBy('shareOfTco')">Share ${this.getSortIcon('shareOfTco')}</th>
                            <th onclick="dashboard.sortBy('costPerUnitCall')">Cost/Call ${this.getSortIcon('costPerUnitCall')}</th>
                        </tr>
                    </thead>
                    <tbody>
                        ${flatData.map(entry => this.createDetailedTableRow(entry)).join('')}
                    </tbody>
                </table>
            </div>
        `;
    }

    getSortIcon(column) {
        if (this.sortColumn !== column) return '↕️';
        return this.sortDirection === 'asc' ? '⬆️' : '⬇️';
    }

    sortBy(column) {
        if (this.sortColumn === column) {
            this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
        } else {
            this.sortColumn = column;
            this.sortDirection = 'asc';
        }
        this.renderDetailedTable();
    }

    createDetailedTableRow(entry) {
        const strategyClass = this.getFundingStrategyClass(entry.fundingStrategy);
        
        return `
            <tr>
                <td><strong>${entry.apiName}</strong></td>
                <td><span class="product-badge">${entry.productId}</span></td>
                <td><span class="funding-strategy ${strategyClass}">${entry.fundingStrategy}</span></td>
                <td class="tco-amount">$${(entry.tco || 0).toLocaleString()}</td>
                <td><span class="share-percentage">${entry.shareOfTco || 'N/A'}</span></td>
                <td>$${(entry.costPerUnitCall || 0).toFixed(2)}</td>
            </tr>
        `;
    }

    renderFilters() {
        const filtersContainer = document.getElementById('filters');
        if (!filtersContainer) return;

        const fundingStrategies = new Set();
        Object.values(this.apiData).forEach(entries => {
            entries.forEach(entry => {
                if (entry.fundingStrategy) {
                    fundingStrategies.add(entry.fundingStrategy);
                }
            });
        });

        filtersContainer.innerHTML = `
            <select id="strategyFilter" onchange="dashboard.filterByStrategy(this.value)">
                <option value="all">All Funding Strategies</option>
                ${Array.from(fundingStrategies).map(strategy => 
                    `<option value="${strategy}">${strategy}</option>`
                ).join('')}
            </select>
            <input type="text" id="searchInput" placeholder="Search APIs or Products..." />
        `;

        // Re-attach search listener
        document.getElementById('searchInput').addEventListener('input', (e) => this.handleSearch(e.target.value));
    }

    filterByStrategy(strategy) {
        this.currentFilter = strategy;
        this.applyFilters();
    }

    handleSearch(searchTerm) {
        this.searchTerm = searchTerm.toLowerCase();
        this.applyFilters();
    }

    applyFilters() {
        this.filteredData = {};

        Object.entries(this.apiData).forEach(([apiName, entries]) => {
            const filteredEntries = entries.filter(entry => {
                // Strategy filter
                if (this.currentFilter !== 'all' && entry.fundingStrategy !== this.currentFilter) {
                    return false;
                }

                // Search filter
                if (this.searchTerm) {
                    const searchableText = `${apiName} ${entry.productId} ${entry.fundingStrategy}`.toLowerCase();
                    if (!searchableText.includes(this.searchTerm)) {
                        return false;
                    }
                }

                return true;
            });

            if (filteredEntries.length > 0) {
                this.filteredData[apiName] = filteredEntries;
            }
        });

        this.renderDashboard();
    }

    renderCharts() {
        this.renderFundingStrategyChart();
        this.renderTcoDistributionChart();
    }

    renderFundingStrategyChart() {
        const chartContainer = document.getElementById('strategyChart');
        if (!chartContainer) return;

        const strategyCounts = {};
        Object.values(this.filteredData).forEach(entries => {
            entries.forEach(entry => {
                const strategy = entry.fundingStrategy || 'Unknown';
                strategyCounts[strategy] = (strategyCounts[strategy] || 0) + 1;
            });
        });

        const total = Object.values(strategyCounts).reduce((a, b) => a + b, 0);

        chartContainer.innerHTML = `
            <h3>Funding Strategy Distribution</h3>
            <div class="chart-bars">
                ${Object.entries(strategyCounts).map(([strategy, count]) => {
                    const percentage = ((count / total) * 100).toFixed(1);
                    return `
                        <div class="chart-bar">
                            <div class="bar-label">${strategy}</div>
                            <div class="bar-container">
                                <div class="bar-fill" style="width: ${percentage}%"></div>
                                <span class="bar-value">${count} (${percentage}%)</span>
                            </div>
                        </div>
                    `;
                }).join('')}
            </div>
        `;
    }

    renderTcoDistributionChart() {
        const chartContainer = document.getElementById('tcoChart');
        if (!chartContainer) return;

        const apiTcos = [];
        Object.entries(this.filteredData).forEach(([apiName, entries]) => {
            if (entries.length > 0) {
                apiTcos.push({ name: apiName, tco: entries[0].tco || 0 });
            }
        });

        apiTcos.sort((a, b) => b.tco - a.tco);
        const maxTco = Math.max(...apiTcos.map(api => api.tco));

        chartContainer.innerHTML = `
            <h3>API Total Cost of Ownership</h3>
            <div class="chart-bars">
                ${apiTcos.map(api => {
                    const percentage = maxTco > 0 ? ((api.tco / maxTco) * 100).toFixed(1) : 0;
                    return `
                        <div class="chart-bar">
                            <div class="bar-label">${api.name}</div>
                            <div class="bar-container">
                                <div class="bar-fill tco-bar" style="width: ${percentage}%"></div>
                                <span class="bar-value">$${api.tco.toLocaleString()}</span>
                            </div>
                        </div>
                    `;
                }).join('')}
            </div>
        `;
    }

    getFundingStrategyClass(strategy) {
        if (!strategy) return 'unknown';
        
        const lowerStrategy = strategy.toLowerCase();
        if (lowerStrategy.includes('equal')) return 'equal-share';
        if (lowerStrategy.includes('rate')) return 'pay-by-rate';
        if (lowerStrategy.includes('profitable')) return 'most-profitable';
        return 'unknown';
    }

    showEmptyState() {
        document.getElementById('emptyState').style.display = 'block';
        document.getElementById('summaryCards').style.display = 'none';
        document.getElementById('mainContent').style.display = 'none';
    }

    hideEmptyState() {
        document.getElementById('emptyState').style.display = 'none';
        document.getElementById('summaryCards').style.display = 'grid';
        document.getElementById('mainContent').style.display = 'block';
    }

    exportData() {
        if (Object.keys(this.filteredData).length === 0) {
            alert('No data to export. Please load data first.');
            return;
        }

        let csvContent = 'API Name,Product ID,Funding Strategy,Total Cost of Ownership,Share of TCO,Cost Per Unit Call\n';
        
        Object.entries(this.filteredData).forEach(([apiName, entries]) => {
            entries.forEach(entry => {
                csvContent += `"${apiName}","${entry.productId}","${entry.fundingStrategy}","${entry.tco}","${entry.shareOfTco}","${entry.costPerUnitCall}"\n`;
            });
        });

        const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
        const link = document.createElement('a');
        
        if (link.download !== undefined) {
            const url = URL.createObjectURL(blob);
            link.setAttribute('href', url);
            link.setAttribute('download', `api-to-product-cost-analysis-${new Date().toISOString().split('T')[0]}.csv`);
            link.style.visibility = 'hidden';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        }
    }
}

// Initialize dashboard when DOM is loaded
let dashboard;
document.addEventListener('DOMContentLoaded', function() {
    dashboard = new ApiCostDashboard();
    dashboard.initialize();
});