import PIL
from PIL import Image
import numpy as np

im = Image.open("../assets/edgetiles.png")
im = np.asarray(im)

M = 10
N = 10


tiles = [im[x:x+M,y:y+N] for x in range(0,im.shape[0],M) for y in range(0,im.shape[1],N)]

for i, tile in enumerate(tiles):
    tile = Image.fromarray(tile)

    tile.save(f"../assets/edgetile-{i}.png")
