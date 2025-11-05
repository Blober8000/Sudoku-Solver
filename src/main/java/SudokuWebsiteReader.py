import pyautogui
from PIL import Image, ImageDraw, ImageFont
import pygetwindow as gw
import time
import math
import subprocess
import os

def almost_round(x, eps=1e-9):
    frac = x - math.floor(x)
    if frac > 1 - eps:  
        return math.ceil(x)
    else:
        return math.floor(x)
    
colours = [
    "#FF0000", #1
    "#008000", #2
    "#0000FF", #3
    "#FFFF00", #4
    "#00FFFF", #5
    "#FF00FF", #6
    "#FFA500", #7
    "#800080", #8
    "#000000"  #9
]

cells = []
for i in range(81):
    cells.append(0)

gw.getAllTitles()

# Pôr em 175%
gw.getWindowsWithTitle("Sudoku Evil - Conquer the Master Level of web Sudoku puzzles - Opera")[0].activate()
time.sleep(2.5)


y_level_x = 870
y_level_y_top = 495
y_level_y_bottom = 1615

for i in range(y_level_y_top, y_level_y_bottom, 1):
    colour = pyautogui.pixel(y_level_x, i)
    pyautogui.moveTo(y_level_x, i)
    if (colour == (52, 72, 97)):
        y_top = i
        break

for i in range(y_level_y_bottom, y_level_y_top, -1):
    colour = pyautogui.pixel(y_level_x, i)
    pyautogui.moveTo(y_level_x, i)
    if (colour == ((52, 72, 97))):
        y_bottom = i
        break

x_level_y = 560
x_level_x_left = 795
x_level_x_right = 1915

for i in range(x_level_x_left, x_level_x_right, 1):
    colour = pyautogui.pixel(i, x_level_y)
    pyautogui.moveTo(i, x_level_y)
    if (colour == ((52, 72, 97))):
        x_left = i
        break

for i in range(x_level_x_right, x_level_x_left, -1):
    colour = pyautogui.pixel(i, y_level_x)
    pyautogui.moveTo(i, x_level_y)
    if (colour == ((52, 72, 97))):
        x_right = i
        break

region = (
    x_left,
    y_top,
    x_right - x_left,
    y_bottom - y_top
)

screenshot = pyautogui.screenshot(region = region)

square_size = 123
x = 4
y = 4
counter_x = 0
counter_y = 0

draw = ImageDraw.Draw(screenshot)
for i in range(9):
    for j in range(9):
       found = False
       out = "gray"

       region_square = (
           x + (square_size * j) + (1*almost_round(counter_x)), 
           y + (square_size * i) + (1*almost_round(counter_y)), 
           x + (square_size * (j+1)) + (1*almost_round(counter_x)), 
           y + (square_size * (i+1)) + (1*almost_round(counter_y))
       )

       region = (
            x_left + 4 + (square_size * j) + (1*almost_round(counter_x)),
            y_top + 4 + (square_size * i) + (1*almost_round(counter_y)),
            (x_left + 4 + (square_size * (j+1)) + (1*almost_round(counter_x))) - (x_left + 4 + (square_size * j) + (1*almost_round(counter_x))),
            (y_top + 4 + (square_size * (i+1)) + (1*almost_round(counter_y))) - (y_top + 4 + (square_size * i) + (1*almost_round(counter_y)))
       )
       pyautogui.click((x_left+4) + square_size*j +10, (y_top + 4) + square_size*i +10)
       pic = screenshot.crop(region_square)
       if (found == False):
             #1
             if (pic.getpixel((65, 45)) == (52, 72, 97)):
                   found = True
                   out = colours[0]
                   cells[i*9+j] = 1
       if (found == False):
             #2
             if (pic.getpixel((77, 87)) == (52, 72, 97)):
                   found = True
                   out = colours[1]
                   cells[i*9+j] = 2   
       if (found == False):
             #4
             if (pic.getpixel((71, 72)) == (52, 72, 97)):
                   found = True
                   out = colours[3]
                   cells[i*9+j] = 4   
       if (found == False):
             #6
             if (pic.getpixel((46, 74)) == (52, 72, 97)):
                   found = True
                   out = colours[5]
                   cells[i*9+j] = 6
       if (found == False):
             #8
             if (pic.getpixel((42, 71)) == (52, 72, 97)):
                   found = True
                   out = colours[7]
                   cells[i*9+j] = 8
       if (found == False):
             #9
             if (pic.getpixel((78, 54)) == (52, 72, 97)):
                   found = True
                   out = colours[8]
                   cells[i*9+j] = 9
       if (found == False):
             #5
             if (pic.getpixel((45, 59)) == (52, 72, 97)):
                   found = True
                   out = colours[4]
                   cells[i*9+j] = 5 
       if (found == False):
             #7
             if (pic.getpixel((59, 72)) == (52, 72, 97)):
                   found = True
                   out = colours[6]
                   cells[i*9+j] = 7
       if (found == False):
             #3
             if (pic.getpixel((61, 32)) == (52, 72, 97)):
                   found = True
                   out = colours[2]
                   cells[i*9+j] = 3

       draw.rectangle(region_square, outline=out, width = 5)
       counter_x += 1/3
    counter_y += 1/3
    counter_x -= 3
    
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
script_dir = os.path.dirname(os.path.abspath(__file__))

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

for i in range(9):
    for j in range(9):

       region_square = (
           x + (square_size * j) + (1*almost_round(counter_x)), 
           y + (square_size * i) + (1*almost_round(counter_y)), 
           x + (square_size * (j+1)) + (1*almost_round(counter_x)), 
           y + (square_size * (i+1)) + (1*almost_round(counter_y))
       )

       region = (
            x_left + 4 + (square_size * j) + (1*almost_round(counter_x)),
            y_top + 4 + (square_size * i) + (1*almost_round(counter_y)),
            (x_left + 4 + (square_size * (j+1)) + (1*almost_round(counter_x))) - (x_left + 4 + (square_size * j) + (1*almost_round(counter_x))),
            (y_top + 4 + (square_size * (i+1)) + (1*almost_round(counter_y))) - (y_top + 4 + (square_size * i) + (1*almost_round(counter_y)))
       )

       pyautogui.click((x_left+4) + square_size*j +10, (y_top + 4) + square_size*i +10)
       pyautogui.write(str(solved_cells[i*9+j]))








