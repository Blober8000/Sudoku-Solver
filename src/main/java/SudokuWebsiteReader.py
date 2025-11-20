import pyautogui
import pygetwindow as gw
from PIL import ImageDraw
import time
import subprocess
import os
import json

script_dir = os.path.dirname(os.path.abspath(__file__))

if (os.path.exists(f"{script_dir}\\SudokuPageProperties.json")):
    with open(f"{script_dir}\\SudokuPageProperties.json", "r") as f:
          data=json.load(f)
else:
    result = subprocess.run(
        ["python", "SudokuPageProperties.py"],
        cwd=script_dir
    )
    with open(f"{script_dir}\\SudokuPageProperties.json", "r") as f:
          data=json.load(f)

cells = []
for i in range(81):
    cells.append(0)

test = gw.getAllTitles()
open = ""
for page in test:
    if (("Sudoku" in page and "Opera" in page)):
        open = page

gw.getWindowsWithTitle(open)[0].activate()
time.sleep(1)


y_top = data["y_top"]
y_bottom = data["y_bottom"]
x_left = data["x_left"]
x_right = data["x_right"]
cell_size = data["cell_size"]
border_width = data["border_width"]
one = data["numbers"]["one"]
two = data["numbers"]["two"]
four = data["numbers"]["four"]
six = data["numbers"]["six"]
eight = data["numbers"]["eight"]
nine = data["numbers"]["nine"]
five = data["numbers"]["five"]
seven = data["numbers"]["seven"]
three = data["numbers"]["three"]

sudokuBlue=(52, 72, 97)
wiggle_room=5

region = (
    x_left-wiggle_room,
    y_top-wiggle_room,
    x_right - x_left+(wiggle_room*2),
    y_bottom - y_top+(wiggle_room*2)
)

screenshot = pyautogui.screenshot(region = region)

width = x_right - x_left+(wiggle_room*2)
height = y_bottom - y_top+(wiggle_room*2)
for i in range(height):
    colour = screenshot.getpixel((int(width/2),i))
    if (colour == sudokuBlue):
        y_top = (y_top-wiggle_room) + i
        break

for i in range(height+1):
    colour = screenshot.getpixel((int(width/2),height-i-1))
    if (colour == sudokuBlue):
        y_bottom = (y_bottom+wiggle_room) - i
        break


for i in range(width):
    colour = screenshot.getpixel((i,int(height/2)))
    if (colour == sudokuBlue):
        x_left = (x_left-wiggle_room) + i
        break

for i in range(height+1):
    colour = screenshot.getpixel((height-i-1,int(height/2)))
    if (colour == sudokuBlue):
        x_right = (x_right+wiggle_room) - i
        break


region = (
    x_left,
    y_top,
    x_right - x_left,
    y_bottom - y_top
)

screenshot = pyautogui.screenshot(region = region)
draw = ImageDraw.Draw(screenshot)

def crosshair(draw, crosshair, colour, region):
    draw.point((region[0] + crosshair[0], region[1] + crosshair[1]), fill=colour)
    draw.point((region[0] + crosshair[0]+1, region[1] + crosshair[1]), fill=colour)
    draw.point((region[0] + crosshair[0], region[1] + crosshair[1]+1), fill=colour)
    draw.point((region[0] + crosshair[0]-1, region[1] + crosshair[1]), fill=colour)
    draw.point((region[0] + crosshair[0], region[1] + crosshair[1]-1), fill=colour)


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

for x in range(9):
    for y in range(9):
       out = "gray"
       region_square = (
            (border_width+1) + (cell_size*x) - (round(0.4*x)), 
            (border_width+1) + (cell_size*y) - (round(0.4*y)),
            border_width + (cell_size-2) + (cell_size*x) - (round(0.4*x)),
            border_width + (cell_size-2) + (cell_size*y) - (round(0.4*y))
        )

       #pyautogui.click(x_left+ border_width + cell_size*y +10, y_top + border_width + cell_size*x +10)
       pic = screenshot.crop(region_square)
             
         # 1 2 4 9 6 8 5 7 3
       if (pic.getpixel(one) == sudokuBlue): #1            
            cells[y*9+x] = 1  
            out=colours[0]
            crosshair(draw, one, out, region_square) 
       elif (pic.getpixel(two) == sudokuBlue): #2                   
            cells[y*9+x] = 2
            out=colours[1]
            crosshair(draw,two, out, region_square) 
       elif (pic.getpixel(four) == sudokuBlue): #4           
            cells[y*9+x] = 4 
            out=colours[2]
            crosshair(draw,four, out, region_square) 
       elif (pic.getpixel(nine) == sudokuBlue): #9                   
            cells[y*9+x] = 9
            out=colours[5]
            crosshair(draw,nine, out, region_square) 
       elif (pic.getpixel(six) == sudokuBlue): #6                  
            cells[y*9+x] = 6
            out=colours[3]
            crosshair(draw,six, out, region_square) 
       elif (pic.getpixel(eight) == sudokuBlue): #8                  
            cells[y*9+x] = 8
            out=colours[4]
            crosshair(draw,eight, out, region_square) 
       elif (pic.getpixel(five) == sudokuBlue): #5           
            cells[y*9+x] = 5 
            out=colours[6]
            crosshair(draw,five, out, region_square) 
       elif (pic.getpixel(seven) == sudokuBlue): #7
            cells[y*9+x] = 7
            out=colours[7]
            crosshair(draw,seven, out, region_square) 
       elif (pic.getpixel(three) == sudokuBlue): #3
            cells[y*9+x] = 3
            out=colours[8]
            crosshair(draw,three, out, region_square) 
       draw.rectangle(region_square, outline=out)
#screenshot.show()

    
for i in range(81): 
    if i == 0: 
        print("{", end="")
    if i != 80: 
        print(f"{cells[i]}, ", end="") 
    else: 
        print(f"{cells[i]}", end="") 
    if i == 80: 
        print("}")

args = [str(i) for i in cells]

result = subprocess.run(
    ["java", "PythonConnect"] + args,
    capture_output=True,
    text=True,
    cwd=script_dir
)

lines = result.stdout.strip().splitlines()

if lines:
    last_line = lines[-1]
    print(last_line)
    if "\u001B" in last_line:
        solved_cells = 0
    else:
        cleaned = last_line.strip("[]")
        string_numbers = cleaned.split(",")
        solved_cells = [int(num.strip()) for num in string_numbers]


if solved_cells != 0:
    for y in range(9):
        for x in range(9):
            if (cells[y*9+x] != 0):
                continue

            region_square = (
                border_width + (cell_size * x), 
                border_width + (cell_size * y), 
                border_width + (cell_size * (x+1))-1, 
                border_width + (cell_size * (y+1))-1
                )

            pyautogui.click((x_left+4) + cell_size*x +10, (y_top + 4) + cell_size*y +10)
            pyautogui.write(str(solved_cells[y*9+x]))
