import PIL
from PIL import Image
import numpy as np

def tile(name, numheight, numwidth):
    im = Image.open("../assets/" + name)
    im = np.asarray(im)

    tilewidth = im.shape[0] // numwidth
    tileheight = im.shape[1] // numheight

    final = []

    for i in range(numwidth):
        for j in range(numheight):
            tile = im[i*tilewidth:(i+1)*tilewidth, j*tileheight:(j+1)*tileheight]
            tile = Image.fromarray(tile)
            final.append(tile)

    return final

def redo_transparency(tileset, color):
    for i in range(len(tileset)):
        tileset[i] = tileset[i].convert("RGBA")
        datas = tileset[i].getdata()
        newData = []
        for item in datas:
            if item[0] == color[0] and item[1] == color[1] and item[2] == color[2]:
                newData.append((255, 255, 255, 0))
            else:
                newData.append(item)
        tileset[i].putdata(newData)
    return tileset

goblin_attack = tile("fight/goblin attack.png", 8, 1)

top_left_color = goblin_attack[0].getpixel((0, 0))

goblin_attack = redo_transparency(goblin_attack, top_left_color)

for i, frame in enumerate(goblin_attack):
    frame.save("../assets/fight/goblinattack/" + str(i) + ".png")


goblin_death = tile("fight/goblin death.png", 8, 1)
goblin_death = redo_transparency(goblin_death, top_left_color)
for i, frame in enumerate(goblin_death):
    frame.save("../assets/fight/goblindeath/" + str(i) + ".png")

goblin_idle = tile("fight/goblin idle.png", 8, 1)
goblin_idle = redo_transparency(goblin_idle, top_left_color)
for i, frame in enumerate(goblin_idle):
    frame.save("../assets/fight/goblinidle/" + str(i) + ".png")


goblin_run = tile("fight/goblin run.png", 8, 1)
goblin_run = redo_transparency(goblin_run, top_left_color)
for i, frame in enumerate(goblin_run):
    frame.save("../assets/fight/goblinrun/" + str(i) + ".png")