#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Thu Mar 18 00:39:56 2021

@author: nimarahimian
"""
import time
import random
from functools import lru_cache
import numpy as np
import matplotlib.pyplot as plt


def marks(n, scenario):
    """Genererar spelarnas positioner på spelbrädet.

    Tilldelar spelare 1 ((n x n + 1) / 2) slumptal och spelare 2 ((n x n - 1) / 2) slumptal.

    Parameters
    ----------
    n : int
     representerar storleken av brädet
    scenario : int
     representerar spelarnas startpositioner

    Returns
    -------
    tuple :
     tupeln innehåller muterbara objekt som representerar spelarnas positioner på spelbrädet.

    Examples
    --------
    >>> marks(3, 2)
    ([5, 7, 4, 8, 9], [1, 6, 3, 2])
    """
    markers = [i for i in range(1, (n*n) + 1)]
    random.shuffle(markers)
    x_s, o_s = [], []

    if scenario == 1:
        x_s.append(markers[0:int((n*n + 1) / 2)])
        o_s.append(markers[int((n*n + 1) / 2):n*n + 1])

        return x_s[0], o_s[0]

    elif scenario == 2:

        markers.remove(((n*n + 1) / 2))
        x_s.append(markers[0:int((n*n - 1) / 2)])
        x_s[0].insert(0, (int((n*n + 1) / 2)))
        o_s.append(markers[int((n * n - 1) / 2):(n * n - 1)])

        return x_s[0], o_s[0]

    return None


@lru_cache()
def board(n):
    """Genererar alla vinnande kombinationer för ett n x n bräde.

    Funktionen skapar en n x n matris för att sedan extrahera raderna,
    kolumnerna och diagonalerna i en lista.

    Parameters
    ----------
    n : int
     storleken av brädet

    Returns
    -------
    list :
     listan innehåller listor som representerar alla vinnande kombinationer.
     Funktionen filtrerar bort kombinationer som inte genererar tre i rad.

    Examples
    --------
    >>> board(3)
    [[1, 4, 7], [2, 5, 8], [3, 6, 9], [1, 2, 3],
    [4, 5, 6], [7, 8, 9], [1, 5, 9], [3, 5, 7]]
    """
    board_size = np.arange(1, n*n + 1).reshape(n, n)
    vertical, horizontal, diagonal, counterdiagonal = [], [], [], []
    wins = []

    for i in range(n):
        vertical.append(board_size[:, i].tolist())
        horizontal.append(board_size[i, :].tolist())
        diagonal.append(board_size.diagonal(i-2).tolist())
        counterdiagonal.append(np.fliplr(board_size).diagonal(i-2).tolist())
        total = vertical + horizontal + diagonal + counterdiagonal

    for j in range(len(total)):
        for i in range(len(total[j])-2):
            wins.append(total[j][0+i:3+i])

    return wins


def gameplay(x_s, n):
    """Analyserar interaktionen mellan spelare 1 och spelare 2

    Parameters
    ----------
    Xs : list
     representerar spelarnas markörer
    n : int
     representerar storleken av brädet

    Returns
    -------
    int :
     ett heltal som representerar spelarnas prestationer på spelbrädet.

    Examples
    --------
    >>> gameplay([1,2,3,4,5], 3)
    19
    """
    y = x_s
    step = []
    countdata = []

    for i in range(1, len(y) + 1):
        step.append(y[0:i])

    countdata = []
    inner_break = False
    for x in range(len(step)):
        if inner_break:
            break
        for y in range(len(board(n))):
            if all(i in step[x] for i in board(n)[y]) == True:
                inner_break = True
                break

            countdata.append(all(elem in step[x] for elem in board(n)[y]))

    if len(countdata) == len(x_s) * len(board(n)):
        return 0

    return len(countdata)


def result(player_1, player_2):
    """Analyserar informationen som funktionen gameplay har genererat.

    Parameters
    ----------
    x : int
     representerar spelare 1:s spelprestation
    y : int
     representerar spelare 2:s spelprestation

    Returns
    -------
    str :
     skriver ut resultatet

    Examples
    --------
    >>> result(18,12)
    Os win!
    """

    if player_1 == 0 and player_2 == 0:
        return "Draw"
    elif player_1 != 0 and player_1 < player_2:
        return "Xs win!"
    elif player_2 != 0 and player_1 > player_2:
        return "Os win!"
    elif player_1 == 0 and player_2 > 0:
        return "Os win!"
    elif player_1 > 0 and player_2 == 0:
        return "Xs win!"


def game(n, scenario):
    """Initierar spelet mellan spelare 1 och spelare 2.

    Parameters
    ----------
    n : int
     representerar storleken av spelbrädet
    scenario : int
     startpositionen för spelare 1

    Returns
    -------
    str :
     skriver ut resultatet

    Examples
    --------
    >>> result(18,12)
    Os win!
    """
    y = marks(n, scenario)
    player1 = (y[0])
    player2 = (y[1])
    # print(player1)
    # print(player2)

    a_1 = gameplay(player1, n)
    b_2 = gameplay(player2, n)
    # print(a)
    # print(b)

    return result(a_1, b_2)


def operator():
    """Möjliggör att användaren kan interagera med koden
    """

    try:
        ui_0 = int(input("How big should the board be (3/5/7)?"))
        ui_1 = int(input("Which scenario do you want to play (1/2)?"))
        ui_2 = int(input("How many games do you want to simulate?"))

        if ui_0 not in (3, 5, 7):
            return "Out-of-bounds"
        elif ui_1 not in (1, 2):
            return "Out-of-bounds"

        start = time.time()

        data = []

        for elem in range(ui_2):
            data.append(game(ui_0, ui_1))

        print("Player 1 wins:", data.count("Xs win!"))
        print("Player 2 wins:", data.count("Os win!"))
        print("Draw:", data.count("Draw"))

        data = [(data.count("Xs win!")), (data.count("Os win!")), (data.count("Draw"))]
        plt.bar([1, 2, 3], data)
        plt.title('Utfall tre-i-rad 3x3 bräde')
        plt.savefig("Utfall.pdf")
        plt.show()

        end = time.time()
        print(end - start)
    except ValueError:
        print("Invalid token")
    except KeyboardInterrupt:
        print("")

    return None

print(operator())

# print(game(3,1))
# print(game(3,2))
# print(game(5,1))
# print(game(5,2))
# print(game(7,1))
# print(game(7,2))
