

import random

# We import the graphics.py library:
#
#   https://mcsp.wartburg.edu/zelle/python/graphics.py
#
# Note: this needs to be downloaded and saved in the same folder as
# this file for things to work!
#
from graphics import *

class Cell:
    def __init__(self,x,y):
        self.x = x
        self.y = y
        
    def coordinates(self):
        return (self.x, self.y)
    


    def neighbors(self):
        
        tomlista = []
   
        x_dx = list((self.x -1, self.x, self.x +1 ))
        y_dy = list((self.y -1, self.y, self.y +1 ))
        d = Cell(x_dx[0], y_dy[y])
        d_2 = Cell(1,2)
        
        
        
        
        a = [(x_dx[0], y_dy[y]) for y in range(len(x_dx))]
        b = [(x_dx[1], y_dy[y]) for y in range(len(x_dx))]
        c = [(x_dx[2], y_dy[y]) for y in range(len(x_dx))]
        merge = a + b + c
        merge.remove((x_dx[1],y_dy[1]))
             
        return merge

#Det är inte koordinaterna som skavara med i listan, utan det är 
       
     
        
    

    # Equality test for cells
    def __eq__(self,other):
        return self.coordinates() == other.coordinates()

    # Hash function for cells (necessary to use cells as keys in a dictionary)
    def __hash__(self):
        return self.coordinates().__hash__()

def randomWorld(width, height):
    """Return a random world"""
    pass

def update(world):
    """Update the world by applying the rules of the game:

    1. Any live cell with two or three live neighbors survives.
    2. Any dead cell with three live neighbors becomes a live cell.
    3. All other live cells die in the next generation. Similarly,
       all other dead cells stay dead.
    """
    pass

c = Cell(2,3)
e = Cell(0,0)
print(c.coordinates())
print(c.neighbors())
print(e.neighbors())


## Extra functions for shifting and combining worlds

def shiftWorld(w,dx=0,dy=0):
    """Shift a world dx steps in x direction and dy steps in y direction"""
    out = {}
    for c in w:
        (x,y) = c.coordinates()
        out[Cell(x+dx,y+dy)] = True
    return out

def unionWorlds(ws):
    """Compute the union of a list of worlds"""
    out = {}
    for w in ws:
        for c in w:
            out[c] = True
    return out


## Some fun starting worlds:

# One glider
# https://www.conwaylife.com/wiki/Glider
glider = { Cell(x,y) : True for (x,y) in [(2,1),(3,2),(1,3),(2,3),(3,3)] }

# Two gliders
# glider2 = unionWorlds([glider,shiftWorld(glider,10)])

# Many gliders and an obstacle
# manygliders = unionWorlds([ shiftWorld(glider,dx,dy)
#                             for dx in [0,10,20]
#                                 for dy in [0,10,20] ] +
#                           [ { Cell(x+32,30) : True for x in range(20) }])


## Code for displaying the world and running the animation

def display(width,height,world, win, rectangles):
    for x in range(width):
        for y in range(height):
            rectangles[y][x].setFill("black" if Cell(x,y) in world else "white")
    win.update()

## The main method
def main():
    # Number of iterations to run animation
    iterations = 1000

    # Size of squares
    k = 10

    # Width of window (will be multiplied by k)
    width  = 60

    # Height of window (will be multiplied by k)
    height = 60

    # Create a window
    win = GraphWin("Game of Life", width * k, height * k, autoflush=False)

    # Create rectangles grid
    rectangles = [ [ Rectangle(Point(j * k, i * k)
                              ,Point((j + 1) * k, (i + 1) * k))
                   for j in range(width)]
                 for i in range(height)]

    # Draw rectangles in window
    for x in range(width):
        for y in range(height):
            rectangles[y][x].draw(win)

    # Initialize the world randomly
    world = randomWorld(width,height)

    # Uncomment to initialize with one glider
    # world = glider

    # Uncomment to initialize with two gliders
    # world = glider2

    # Uncomment to initialize with many gliders and an obstacle
    # world = manygliders

    # Run simulation "iterations" number of steps
    for i in range(iterations):
        # Draw the world
        display(width,height,world,win,rectangles)

        # Update the state of the world
        world = update(world)

    # Close the window and quit when we are done
    win.close()
    quit()

# Uncomment to run the animation
# main()
