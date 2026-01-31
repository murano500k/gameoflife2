# Store Assets

This folder contains assets for Google Play Store listing.

## Files

- `icon-512.svg` - App icon source (convert to 512x512 PNG)
- `feature-graphic.svg` - Feature graphic source (convert to 1024x500 PNG)

## Converting SVG to PNG

### Option 1: Online converter
Use https://svgtopng.com/ or https://cloudconvert.com/svg-to-png

### Option 2: Using Inkscape (if installed)
```bash
inkscape icon-512.svg -w 512 -h 512 -o icon-512.png
inkscape feature-graphic.svg -w 1024 -h 500 -o feature-graphic.png
```

### Option 3: Using ImageMagick (if installed)
```bash
convert -background none icon-512.svg -resize 512x512 icon-512.png
convert -background none feature-graphic.svg -resize 1024x500 feature-graphic.png
```

### Option 4: Using rsvg-convert (if installed)
```bash
rsvg-convert -w 512 -h 512 icon-512.svg -o icon-512.png
rsvg-convert -w 1024 -h 500 feature-graphic.svg -o feature-graphic.png
```

## Google Play Store Requirements

| Asset | Size | Format |
|-------|------|--------|
| App icon | 512 x 512 px | PNG (32-bit, with alpha) |
| Feature graphic | 1024 x 500 px | PNG or JPEG |
| Phone screenshots | 1080 x 1920 px (or similar) | PNG or JPEG |

## Screenshots

Take screenshots from the running app showing:
1. Main game screen with a pattern running (Pulsar recommended)
2. Pattern selection screen
3. Pattern detail with facts
4. Gosper Glider Gun in action
5. Clean empty grid UI
