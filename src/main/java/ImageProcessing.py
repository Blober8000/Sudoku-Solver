from PIL import Image, ImageDraw

screenshots = []
file_paths =[
    "C:\\Users\\vasco\\Documents\\SudokuTest\\1_edit.png",
    "C:\\Users\\vasco\\Documents\\SudokuTest\\2_edit.png",
    "C:\\Users\\vasco\\Documents\\SudokuTest\\4_edit.png",
    "C:\\Users\\vasco\\Documents\\SudokuTest\\6_edit.png",
    "C:\\Users\\vasco\\Documents\\SudokuTest\\8_edit.png",
    "C:\\Users\\vasco\\Documents\\SudokuTest\\9_edit.png",
    "C:\\Users\\vasco\\Documents\\SudokuTest\\5_edit.png",
    "C:\\Users\\vasco\\Documents\\SudokuTest\\7_edit.png",
    "C:\\Users\\vasco\\Documents\\SudokuTest\\3_edit.png"
]
for path in file_paths:
    screenshots.append(Image.open(path))
screenshot = Image.open("C:\\Users\\vasco\\Documents\\SudokuTest\\empty_edit.png")

colours = [
    "#FF0000", #1
    "#008000", #2
    "#FFFF00", #4
    "#FF00FF", #6
    "#800080", #8
    "#000000", #9
    "#00FFFF", #5
    "#FFA500", #7
    "#0000FF" #3  
]

numbers = [
    1,
    2,
    3,
    4,
    5,
    6,
    7,
    8,
    9
]

pics = screenshots.copy()

draw = ImageDraw.Draw(screenshot)
shift = -1
for number in numbers:
    for pixelx in range(123):
        for pixely in range(123):
            draw.rectangle((pixelx, pixely, pixelx, pixely), outline="#ffffff")

            colour = screenshots[number-1].getpixel((pixelx, pixely))
            if (colour == (0, 0, 0)):
                draw.rectangle((pixelx, pixely, pixelx, pixely), outline=colours[number-1])
            for index, pic in enumerate(pics):
                color = pic.getpixel((pixelx,pixely))
                if (color == (0, 0, 0) and index != (number + shift)):
                    draw.rectangle((pixelx, pixely, pixelx, pixely), outline="#ffffff")
    pics.remove(pics[0])
    shift -= 1


    screenshot.save(f"C:\\Users\\vasco\\Documents\\SudokuTest\\LonePixels_{number}.png")

