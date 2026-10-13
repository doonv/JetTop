#!/bin/sh
# Regenerate the bundled Google Sans file.
# Source: GoogleSans[GRAD,opsz,wght].ttf from
# https://github.com/google/fonts/tree/main/ofl/googlesans
# (place it next to this script). Subsets to Latin + Hebrew.
set -e

SRC=GoogleSans-VariableFont_GRAD,opsz,wght.ttf
UNICODES="U+0000-00FF,U+0131,U+0152-0153,U+02BB-02BC,U+02C6,U+02DA,U+02DC,U+2000-206F,U+20AC,U+2122,U+2191,U+2193,U+2212,U+2215,U+FEFF,U+FFFD,U+0590-05FF,U+FB1D-FB4F,U+20AA,U+25CC"

pyftsubset "$SRC" \
  --output-file=app/src/main/res/font/googlesans_lat_he.ttf \
  --unicodes="$UNICODES" \
  --layout-features='*' --no-hinting
