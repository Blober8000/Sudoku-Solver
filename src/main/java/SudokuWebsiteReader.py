import pyautogui
from PIL import Image, ImageDraw, ImageFont
import pygetwindow as gw
import time
import math
import subprocess
import os
import json

script_dir = os.path.dirname(os.path.abspath(__file__))

if (os.path.exists(f"{script_dir}\\SudokuPageProperties.json")):
    with open(f"{script_dir}\\SudokuPageProprerties.json", "r") as f:
          data=json.load(f)
else:
    result = subprocess.run(
        ["python", "SudokuPagePropreties.py"],
        cwd=script_dir
    )
    with open(f"{script_dir}\\SudokuPageProprerties.json", "r") as f:
          data=json.load(f)

cells = []
for i in range(81):
    cells.append(0)

# Pôr em 175%
test = gw.getAllTitles()
open = ""
for page in test:
    if (("Sudoku" in page)):
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

for x in range(9):
    for y in range(9):
       out = "gray"

       region_square = (
        border_width + (cell_size * x), 
        border_width + (cell_size * y), 
        border_width + (cell_size * (x+1))-1, 
        border_width + (cell_size * (y+1))-1
        )

       pyautogui.click(x_left+ border_width + cell_size*y +10, y_top + border_width + cell_size*x +10)
       pic = screenshot.crop(region_square)
             
       if (pic.getpixel(one) == sudokuBlue): #1            
            cells[y*9+x] = 1   
       elif (pic.getpixel(two) == sudokuBlue): #2                   
            cells[y*9+x] = 2  
       elif (pic.getpixel(four) == sudokuBlue): #4           
            cells[y*9+x] = 4 
       elif (pic.getpixel(six) == sudokuBlue): #6                  
            cells[y*9+x] = 6
       elif (pic.getpixel(eight) == sudokuBlue): #8                  
            cells[y*9+x] = 8
       elif (pic.getpixel(nine) == sudokuBlue): #9                   
            cells[y*9+x] = 9
       elif (pic.getpixel(five) == sudokuBlue): #5           
            cells[y*9+x] = 5 
       elif (pic.getpixel(seven) == sudokuBlue): #7
            cells[y*9+x] = 7
       elif (pic.getpixel(three) == sudokuBlue): #3
            cells[y*9+x] = 3

    
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
else:
    print("No output from Java.")

cleaned = last_line.strip("[]")
string_numbers = cleaned.split(",")
solved_cells = [int(num.strip()) for num in string_numbers]

for y in range(9):
    for x in range(9):

       region_square = (
        border_width + (cell_size * x), 
        border_width + (cell_size * y), 
        border_width + (cell_size * (x+1))-1, 
        border_width + (cell_size * (y+1))-1
        )

       pyautogui.click((x_left+4) + cell_size*x +10, (y_top + 4) + cell_size*y +10)
       pyautogui.write(str(solved_cells[y*9+x]))
