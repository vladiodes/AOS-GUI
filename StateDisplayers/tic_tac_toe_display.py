import matplotlib.pyplot as plt

def display(state, filename = None,is_merging_images = False):
    grid = state['grid']
    # Create a 3x3 grid for the tic tac toe board
    board = [[0, 0, 0], [0, 0, 0], [0, 0, 0]]

    # Fill the board with X's and O's based on the grid array
    for i in range(3):
        for j in range(3):
            if grid[i * 3 + j] == 1:
                board[i][j] = 'X'
            elif grid[i * 3 + j] == 2:
                board[i][j] = 'O'

    # Plot the tic tac toe board
    fig, ax = plt.subplots()
    ax.axis('off')

    # Draw horizontal lines
    ax.plot([0, 3], [1, 1], 'k-')
    ax.plot([0, 3], [2, 2], 'k-')

    # Draw vertical lines
    ax.plot([1, 1], [0, 3], 'k-')
    ax.plot([2, 2], [0, 3], 'k-')

    # Place X's and O's on the board
    for i in range(3):
        for j in range(3):
            if board[i][j] == 'X':
                ax.text(j + 0.5, i + 0.5, 'X', fontsize=50, va='center', ha='center')
            elif board[i][j] == 'O':
                ax.text(j + 0.5, i + 0.5, 'O', fontsize=50, va='center', ha='center')


    plt.savefig(fname=filename,format='PNG')