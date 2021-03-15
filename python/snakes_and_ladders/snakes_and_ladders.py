class SnakesLadders:
    """Store and process turns for Snakes and Ladders"""
    def __init__(self):
        self.player = 0
        self.positions = [0, 0]
        self.__snakes_ladders = {
            16: 6, 49: 11, 62: 19, 46: 25, 74: 53, 64: 60, 89: 68, 95: 75, 99: 80, 92: 88,
            2: 38, 7: 14, 8: 31, 15: 26, 21: 42, 28: 84, 36: 44, 51: 67, 71: 91, 78: 98, 87: 94
        }
        pass

    def __update_player(self, die1: int, die2: int) -> None:
        """Change to next player if die1 != die2"""
        if die1 != die2:
            self.player = (self.player + 1) % 2

    def __process_move(self, die1, die2) -> None:
        """Update player position"""
        self.positions[self.player] += die1 + die2
        if self.positions[self.player] > 100:
            self.positions[self.player] = 100 - self.positions[self.player] % 100
        if self.positions[self.player] in self.__snakes_ladders:
            self.positions[self.player] = self.__snakes_ladders[self.positions[self.player]]

    def play(self, die1: int, die2: int) -> str:
        """Process one turn on initialized class given two die rolls. Returns game state as string."""
        if 100 in self.positions:
            return "Game over!"
        self.__process_move(die1, die2)
        if 100 in self.positions:
            return f"Player {self.player + 1} Wins!"
        message = f"Player {self.player + 1} is on square {self.positions[self.player]}"
        self.__update_player(die1, die2)
        return message
