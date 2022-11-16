import PIL
from PIL import Image
import numpy as np

im = Image.open("../assets/Map.jpg")
im = np.asarray(im)

M = 16
N = 16

colormap = {
    "BLUE": (0, 0, 255),
    "BLACK": (0, 0, 0),
    "YELLOW": (255, 255, 0),
    "BROWN": (165, 42, 42),
    "LIGHTGREEN": (144, 238, 144),
    "GREEN": (0, 128, 0),
}

tilefilemap = {
    "BLUE": "o",
    "BLACK": "x",
    "YELLOW": ".",
    "BROWN": "=",
    "LIGHTGREEN": "#",
    "GREEN": "+",
}

tiles = [im[x:x+M,y:y+N] for x in range(0,im.shape[0],M) for y in range(0,im.shape[1],N)]

fl = ""

for i, tile in enumerate(tiles):
    tile = Image.fromarray(tile)

    # Check if width and height are correct
    if tile.size[0] != M or tile.size[1] != N:
        print("Tile {} has wrong size, newline printed".format(i))
        fl += "\n"
        continue

    # Get Color in the middle of the tile
    color = tile.getpixel((M//2, N//2))
    color = tuple(color)

    # Get the nearest color from the colormap
    nearest_color = min(colormap, key=lambda x: sum((colormap[x][i]-color[i])**2 for i in range(3)))

    # Get the tilefilemap value
    tilefilemap_value = tilefilemap[nearest_color]

    fl += tilefilemap_value

with open("../assets/overworld.wof", "w") as f:
    f.write(fl)
