import pyautogui
from PIL import ImageDraw
import pygetwindow as gw
import time
import math
import json
import os


pages = gw.getAllTitles()
page = ""
for pag in pages:
    if (("Sudoku" in pag)):
        page = pag

gw.getWindowsWithTitle(page)[0].activate()
time.sleep(1)

width, height = pyautogui.size()
width = width-1
height = height-1

regionY = (
    int(width/3),
    0,
    1,
    height
)

regionX = (
    0,
    int(height/2),
    width,
    1
)

screenshotY = pyautogui.screenshot(region=regionY)
screenshotX = pyautogui.screenshot(region=regionX)

for i in range(width):
    colour = screenshotX.getpixel((i,0))
    if (colour == (52, 72, 97)):
        x_left = i
        break

for i in range(height+1):
    colour = screenshotX.getpixel((height-i-1,0))
    if (colour == (52, 72, 97)):
        x_right = height-i-1
        break

for i in range(height):
    colour = screenshotY.getpixel((0,i))
    if (colour == (52, 72, 97)):
        y_top = i
        break

y_bottom = y_top + (x_right-x_left)


print(f"({x_left}, {y_top})")
print(f"({x_right}, {y_bottom})")

regionBorder = (
    int(width/3),
    y_top - 5,
    1,
    25
)

screenshotBorder = pyautogui.screenshot(region=regionBorder)

counter = 0
for i in range(24):
    colour = screenshotBorder.getpixel((0,i))
    if (colour == (52, 72, 97)):
        counter = counter + 1

border_width = counter

print(border_width)

cell_size = int((y_bottom-y_top)/9)

print(cell_size)

regionBoard = (
    x_left,
    y_top,
    x_right-x_left,
    y_bottom-y_top
)

screenshotBoard = pyautogui.screenshot(region=regionBoard)

cellsStr = []

for i in range(9):
    cellsStr.append(input(f"Introduza as coordenadas de uma célula com um {i+1} (row:1, colum:1): "))
cellsStr.append(input(f"Introduza as coordenadas de uma célula vazia (row:1, colum:1): "))


cells = []
for S in cellsStr:
    cleaned = S.strip("()")
    string_numbers = cleaned.split(",")
    numbers = [int(num.strip()) for num in string_numbers]
    numbers0 = [numbers[0]-1, numbers[1]-1]
    cells.append(numbers0)

all_cells = []
draw = ImageDraw.Draw(screenshotBoard)
for x in range(9):
    for y in range(9):
        regionCell = (
            (border_width+1) + (cell_size*x) - (round(0.4*x)), 
            (border_width+1) + (cell_size*y) - (round(0.4*y)),
            border_width + (cell_size-2) + (cell_size*x) - (round(0.4*x)),
            border_width + (cell_size-2) + (cell_size*y) - (round(0.4*y))
        )
        pic = screenshotBoard.crop(regionCell)
        all_cells.append(pic)

cell_pics = []
for i in range(10):
    arr = cells[i]
    cell_pics.append(all_cells[(arr[0]*9)+arr[1]])

colours = [
    "#FF0000", #1
    "#008000", #2
    "#FFFF00", #4
    "#000000", #9
    "#FF00FF", #6
    "#800080", #8
    "#00FFFF", #5
    "#FFA500", #7
    "#0000FF" #3  
]

# 1 2 3 4 5 6 7 8 9
temp = cell_pics[2]
cell_pics[2] = cell_pics[3] #3<->4
cell_pics[3] = temp

# 1 2 4 3 5 6 7 8 9
temp = cell_pics[3]
cell_pics[3] = cell_pics[8] #3<->9
cell_pics[8] = temp

# 1 2 4 9 5 6 7 8 3
temp = cell_pics[4]
cell_pics[4] = cell_pics[5] #5<->6
cell_pics[5] = temp

# 1 2 4 9 6 5 7 8 3
temp = cell_pics[6]
cell_pics[6] = cell_pics[7] #7<->8
cell_pics[7] = temp

# 1 2 4 9 6 5 8 7 3
temp = cell_pics[5]
cell_pics[5] = cell_pics[6] #5<->8
cell_pics[6] = temp

# 1 2 4 9 6 8 5 7 3

canvas = cell_pics[9].copy()
screenshots = []
for i in range(9):
    screenshots.append(cell_pics[i].copy())
pics = screenshots.copy()


cell_size = cell_size - 3
for i, pic in enumerate(pics):
    draw = ImageDraw.Draw(canvas)
    for pixelx in range(cell_size):
        for pixely in range(cell_size):
            draw.point((pixelx, pixely), fill="#ffffff")
            colour = pic.getpixel((pixelx, pixely))
            if(colour == (52, 72, 97) and pixelx!=0 and pixelx!=1 and pixelx!=2 and pixelx!=cell_size-1 and pixelx!=cell_size-2 and pixelx!=cell_size-3 and pixely!=0 and pixely!=1 and pixely!=2 and pixely!=cell_size-1 and pixely!=cell_size-2 and pixely!=cell_size-3):
                draw.point((pixelx, pixely), fill="#000000")
            else:
                draw.point((pixelx, pixely), fill="#ffffff")
    pics[i] = canvas.copy()

screenshots = pics.copy()

identifiable = []
draw = ImageDraw.Draw(canvas)
shift = -1
for number in range(1,10,1):
    for pixelx in range(cell_size):
        for pixely in range(cell_size):
            draw.point((pixelx, pixely), fill="#ffffff")

            colour = screenshots[number-1].getpixel((pixelx, pixely))
            if (colour == (0, 0, 0)):
                draw.point((pixelx, pixely), fill=colours[number-1])
            for index, pic in enumerate(pics):
                color = pic.getpixel((pixelx,pixely))
                if (color == (0, 0, 0) and index != (number + shift)):
                    draw.point((pixelx, pixely), fill="#ffffff")
    pics.remove(pics[0])
    shift -= 1

    identifiable.append(canvas.copy())

crosshair = []
for ind, pic in enumerate(identifiable):
    found=False
    for pixelx in range(1,cell_size-1,1):
        for pixely in range(1,cell_size-1,1):
            colourC = pic.getpixel((pixelx,pixely))
            colourN = pic.getpixel((pixelx,pixely-1))
            colourNE = pic.getpixel((pixelx+1,pixely-1))
            colourE = pic.getpixel((pixelx+1,pixely))
            colourSE = pic.getpixel((pixelx+1,pixely+1))
            colourS = pic.getpixel((pixelx,pixely+1))
            colourSW = pic.getpixel((pixelx-1,pixely+1))
            colourW = pic.getpixel((pixelx-1,pixely))
            colourNW = pic.getpixel((pixelx-1,pixely-1))

            if (colourC != (255,255,255) and colourC == colourN and colourC == colourE and colourC == colourS and colourC == colourW and colourC == colourNE and colourC == colourNW and colourC == colourSE and colourC == colourSW):
                crosshair.append((pixelx, pixely))
                draw = ImageDraw.Draw(pic)
                draw.point((pixelx, pixely), fill="#00ff00")

                draw.point((pixelx, pixely-1), fill="#00ff00")
                draw.point((pixelx+1, pixely), fill="#00ff00")
                draw.point((pixelx, pixely+1), fill="#00ff00")
                draw.point((pixelx-1, pixely), fill="#00ff00")

                draw.point((pixelx+1, pixely-1), fill="#00ff00")
                draw.point((pixelx+1, pixely+1), fill="#00ff00")
                draw.point((pixelx-1, pixely+1), fill="#00ff00")
                draw.point((pixelx-1, pixely-1), fill="#00ff00")
                found=True
                pic.show()
                break
        
        if(found == True):
            break

script_dir = os.path.dirname(os.path.abspath(__file__))

cell_size = cell_size + 3

# 1 2 4 9 6 8 5 7 3
with open(f"{script_dir}\\SudokuPageProprerties.json", "w") as f:
    data = {
        "y_top": y_top,
        "y_bottom": y_bottom,
        "x_left": x_left,
        "x_right": x_right,
        "border_width": border_width,
        "cell_size": cell_size,
        "numbers": {
            "one": crosshair[0],
            "two": crosshair[1],
            "four": crosshair[2],
            "nine": crosshair[3],
            "six": crosshair[4],
            "eight": crosshair[5],
            "five": crosshair[6],
            "seven": crosshair[7],
            "three": crosshair[8] 
        }
    }
    json.dump(data, f, indent=4)



