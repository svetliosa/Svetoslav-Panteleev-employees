# Employee Pair Longest Project Time

Angular application that finds the pair of employees who have worked together on common projects for the longest period of time.

## Features

- Upload CSV files with employee collaboration data
- Display data in a responsive table
- Find longest collaboration pair
- Works on desktop, tablet, and mobile

## Installation

1. Install Angular CLI:
   ```bash
   npm install -g @angular/cli
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the application:
   ```bash
   ng serve
   ```

4. Open `http://localhost:4200` in your browser

## CSV Format

Your CSV file should have this structure:

```csv
EmpID, ProjectID, DateFrom, DateTo
143, 12, 2013-11-01, 2014-01-05
218, 10, 2012-05-16, NULL
143, 10, 2009-01-01, 2011-04-27
```

## Backend API

The app expects a Java backend running on `http://localhost:8080` with these endpoints:

- `POST /api/load-csv-data` - Load and display CSV data
- `POST /api/upload` - Find longest collaboration pair

## Usage

1. Click "Choose File" to select a CSV file
2. Data will automatically load and display in the datagrid
3. Click "Upload" to find the longest collaboration pair
4. Result will be displayed below the upload section

## Build for Production

```bash
ng build
```

Files will be created in the `dist/` folder. 