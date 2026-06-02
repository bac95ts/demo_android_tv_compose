const fs = require('fs');
const path = require('path');
const csv = require('csv/sync');

// Helper to convert category/key to snake_case for Android XML naming
function toSnakeCase(str) {
  if (!str) return '';
  return str
    // Replace non-alphanumeric characters (including parentheses) with underscore
    .replace(/[^a-zA-Z0-9]/g, '_')
    // Insert underscore before capital letters for camelCase
    .replace(/([a-z0-9])([A-Z])/g, '$1_$2')
    // Replace multiple underscores with a single underscore
    .replace(/_+/g, '_')
    // Remove leading/trailing underscores
    .replace(/^_+|_+$/g, '')
    // Convert to lowercase
    .toLowerCase();
}

// Helper to escape XML special characters for Android strings.xml
function escapeXml(unsafe) {
  if (!unsafe) return '';
  return unsafe
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '\\"')
    .replace(/'/g, "\\'");
}

// Map CSV locale header to Android res folder name
function getAndroidFolder(locale) {
  const loc = locale.trim();
  if (loc === 'en-US' || loc === 'en') {
    return 'values';
  }
  if (loc === 'vi-VN' || loc === 'vi') {
    return 'values-vi';
  }
  
  // Fallback for other locales if any
  const parts = loc.split('-');
  const lang = parts[0].toLowerCase();
  if (parts.length > 1) {
    const region = parts[1].toUpperCase();
    return `values-${lang}-r${region}`;
  }
  return `values-${lang}`;
}

const files = fs.readdirSync(__dirname).filter((fn) => fn.endsWith('.csv'));

if (files.length <= 0) {
  console.log('No csv file found in translate directory.');
  process.exit(1);
}

const csvFile = path.join(__dirname, files[0]);
console.log(`Processing CSV file: ${csvFile}`);

const loadData = csv.parse(fs.readFileSync(csvFile, 'utf-8'));

if (loadData.length <= 0) {
  console.log('CSV file is empty.');
  process.exit(1);
}

// The header row: e.g. ["category", "key", "vi-VN", "en-US"]
const headers = loadData[0];
const titles = headers.filter((d) => d !== '').slice(2, headers.length);

if (titles.length <= 0) {
  console.log('No language columns found in CSV.');
  process.exit(1);
}

console.log(`Found language columns: ${titles.join(', ')}`);

// Initialize results for each locale
const results = {};
titles.forEach((locale) => {
  results[locale] = [];
});

let currentCategory = '';

for (let i = 1; i < loadData.length; i++) {
  const row = loadData[i];
  if (!row || row.length < 2) continue;
  
  if (row[0] && row[0].trim() !== '') {
    currentCategory = row[0].trim();
  }
  
  const key = row[1] ? row[1].trim() : '';
  if (key === '') continue; // skip rows without a key
  
  // Build Android string name
  let keyName = '';
  if (currentCategory !== '') {
    keyName = `${toSnakeCase(currentCategory)}_${toSnakeCase(key)}`;
  } else {
    keyName = toSnakeCase(key);
  }
  
  // Store values for each locale column
  for (let t = 0; t < titles.length; t++) {
    const locale = titles[t];
    // CSV column index for this locale is 2 + t
    const val = row[2 + t] !== undefined ? row[2 + t].trim() : '';
    results[locale].push({ keyName, value: val });
  }
}

// Generate the Android strings.xml files
titles.forEach((locale) => {
  const folder = getAndroidFolder(locale);
  const targetDir = path.join(__dirname, '..', 'app', 'src', 'main', 'res', folder);
  const targetFile = path.join(targetDir, 'strings.xml');
  
  // Ensure target folder exists
  if (!fs.existsSync(targetDir)) {
    fs.mkdirSync(targetDir, { recursive: true });
  }
  
  let xmlContent = '<?xml version="1.0" encoding="utf-8"?>\n<resources>\n';
  
  results[locale].forEach((item) => {
    const escapedValue = escapeXml(item.value);
    xmlContent += `    <string name="${item.keyName}">${escapedValue}</string>\n`;
  });
  
  xmlContent += '</resources>\n';
  
  fs.writeFileSync(targetFile, xmlContent, 'utf-8');
  console.log(`Generated Android strings.xml for locale ${locale} -> ${targetFile}`);
});

console.log('Android translation generation completed successfully.');
