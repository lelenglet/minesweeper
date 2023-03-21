# Démineur

## Classes

Cell : a class which represents a cell on the game's grid
Grid : a class which represents the game's plate
Game : an (interface ??) which launch the game

### Cell

#### Attribute

value (int) : value of the case

- -1 for a mine
- 0,1,... for other cell

neighbor (EnumMap<Direction,Cell>) : the list of the neighbor according to their direction.

marqued (boolean) : tell if the cell is marked

#### Methods

getMarqued () : int

- return the state of the cell

setMarqued () : void

- set marqued according to the current state of the cell
  - 1 if the cell is hidden
  - 0 if the cell is already marqued
  - doesn't change if the cell is uncovered

seekNeighbor(Cell neighbor) : Direction

- browse the map in order to find the target in the neighborhood of the cell.
- return a direction if the cell's the cell in its neighbor
- return null if not

### Grid

#### Attribute

height (int) : number of cells in a column

width (int) : number of cells in a line

gamePlate (Cell[][]) : 2D table which stocks all the cells

nbMine (int) : number of mines in the grid

### Game

#### Attribute

#### Methods

newGame () : void

- display the menu of the game

chooseLevel (int level) : void

- the player choose the level on keyboard

lauchGame (String filePath) : void

saveGame (String filePath) : void

## Enumeration

Direction : an enumeration of the different direction possible
