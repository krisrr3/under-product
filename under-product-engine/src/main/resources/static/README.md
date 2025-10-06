# API to Product Cost Analysis Dashboard

This frontend provides a comprehensive visualization of API cost allocation across your EV product portfolio.

## Features

### 📊 Overview Dashboard
- **Summary Cards**: Display key metrics (Total APIs, Products, Relationships, TCO)
- **API Sections**: Grouped view showing cost breakdown per API
- **Real-time Data**: Live updates from the backend service

### 📋 Detailed Table View
- **Sortable Columns**: Click column headers to sort data
- **Complete Information**: All cost entry details in tabular format
- **Responsive Design**: Works on desktop, tablet, and mobile

### 📈 Analytics & Charts
- **Funding Strategy Distribution**: Visual breakdown of strategy usage
- **TCO Distribution**: Comparative view of API costs
- **Interactive Charts**: Hover for detailed information

### 🔍 Advanced Filtering
- **Strategy Filter**: Filter by funding strategy type
- **Search**: Find specific APIs or products
- **Dynamic Updates**: Filters apply across all views

### 📤 Export Capabilities
- **CSV Export**: Download filtered data
- **Print Support**: Optimized for printing
- **Date Stamped**: Exports include timestamp

## File Structure

```
src/main/resources/static/
├── index.html              # Basic dashboard (recommended for production)
├── advanced-dashboard.html  # Enhanced dashboard with tabs and analytics
├── dashboard.js            # JavaScript functionality and API calls
├── dashboard.css          # Additional styling and responsive design
└── README.md              # This file
```

## Access URLs

After starting your Spring Boot application, access the dashboard at:

- **Basic Dashboard**: `http://localhost:8080/` or `http://localhost:8080/index.html`
- **Advanced Dashboard**: `http://localhost:8080/advanced-dashboard.html`
- **API Endpoint**: `http://localhost:8080/api/cost-breakdown/api-to-product-report`

## Usage Instructions

### 1. Starting the Application
```bash
# Navigate to your project directory
cd under-product-engine

# Run with Maven
./mvnw spring-boot:run

# Or run with Java (after building)
java -jar target/under-product-engine-0.0.1-SNAPSHOT.war
```

### 2. Accessing the Dashboard
1. Open your web browser
2. Navigate to `http://localhost:8080`
3. Click "Refresh Data" to load the latest cost analysis
4. Explore different views using the tabs

### 3. Understanding the Data

#### Funding Strategies
- **Equal Share (ES)**: Cost split equally among all products
- **Pay by Rate (PbR)**: Cost allocated based on API usage frequency
- **Most Profitable Product (MPP)**: Most profitable product pays full cost

#### Cost Metrics
- **TCO**: Total Cost of Ownership for the API
- **Share of TCO**: Percentage or description of cost allocation
- **Cost Per Unit Call**: Average cost per API call

### 4. Filtering and Searching
- Use the strategy dropdown to filter by funding model
- Type in the search box to find specific APIs or products
- All views update automatically with applied filters

### 5. Exporting Data
- Click "Export to CSV" to download current filtered data
- File includes timestamp and all visible entries
- Compatible with Excel and other spreadsheet applications

## Customization

### Adding New Charts
To add custom visualizations, modify `dashboard.js`:

```javascript
// Add to renderCharts() method
renderCustomChart() {
    // Your chart implementation
}
```

### Styling Changes
Modify `dashboard.css` or the `<style>` section in HTML files:

```css
/* Custom color scheme */
.summary-card {
    background: linear-gradient(135deg, #your-color1, #your-color2);
}
```

### API Integration
The dashboard connects to the Spring Boot backend via:
- Endpoint: `/api/cost-breakdown/api-to-product-report`
- Method: GET
- Response: JSON object with API-to-product mappings

## Browser Compatibility

- ✅ Chrome 70+
- ✅ Firefox 65+
- ✅ Safari 12+
- ✅ Edge 79+
- ✅ Mobile browsers (iOS Safari, Chrome Mobile)

## Performance Notes

- Data is cached after loading for faster filtering
- Charts render progressively for better user experience
- Responsive design adapts to screen size automatically
- Print styles optimize for documentation

## Troubleshooting

### Common Issues

1. **"No Data Available"**
   - Check if the Spring Boot application is running
   - Verify the API endpoint is accessible at `/api/cost-breakdown/api-to-product-report`
   - Check browser console for network errors

2. **Slow Loading**
   - Large datasets may take time to render
   - Consider implementing pagination for production use

3. **Export Not Working**
   - Modern browsers required for CSV download
   - Check if pop-up blocking is preventing download

### Development Mode

For development, you can serve the static files independently:

```bash
# Using Python 3
python -m http.server 8000

# Using Node.js (if you have http-server installed)
npx http-server

# Then modify the fetch URL in dashboard.js to include full server address
```

## Security Considerations

- Dashboard uses relative URLs for API calls
- No authentication implemented (add as needed)
- CORS may need configuration for cross-origin requests
- Consider HTTPS in production environments

## Future Enhancements

Potential improvements for production use:
- Real-time data updates with WebSocket
- User authentication and role-based access
- Drill-down capabilities for detailed analysis
- Historical data and trend analysis
- API rate limiting and caching
- Advanced filtering with date ranges
- Bookmark/save filter configurations

## Support

For issues or questions:
1. Check the browser console for JavaScript errors
2. Verify API endpoint responses
3. Test with sample data first
4. Review Spring Boot application logs