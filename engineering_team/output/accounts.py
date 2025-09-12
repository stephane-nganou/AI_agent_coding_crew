def get_share_price(symbol: str) -> float:
    """
    Test implementation returning fixed prices for specific symbols.
    In production, this would connect to a real market data API.
    
    Args:
        symbol: The stock symbol to get the price for
        
    Returns:
        The current price of the share, or 0.0 if symbol is unknown
    """
    prices = {
        'AAPL': 150.0,
        'TSLA': 650.0,
        'GOOGL': 2800.0
    }
    return prices.get(symbol, 0.0)


class Account:
    """
    A simple account management system for a trading simulation platform.
    
    This class allows users to create accounts, manage funds, record share transactions,
    and generate portfolio reports.
    """
    
    def __init__(self, username: str, initial_deposit: float) -> None:
        """
        Initialize a new account with a username and initial deposit.
        
        Args:
            username: The username of the account holder
            initial_deposit: The initial amount of money to deposit into the account
        """
        self.username = username
        self.initial_deposit = initial_deposit
        self.balance = initial_deposit
        self.holdings = {}  # Dict[str, int] - symbol to quantity mapping
        self.transactions = []  # List[Dict] - transaction records
        
        # Record the initial deposit as a transaction
        self.record_transaction('deposit', amount=initial_deposit)
    
    def deposit(self, amount: float) -> None:
        """
        Deposit funds into the account.
        
        Args:
            amount: The amount to deposit (must be positive)
            
        Raises:
            ValueError: If amount is not positive
        """
        if amount <= 0:
            raise ValueError("Deposit amount must be positive")
        
        self.balance += amount
        self.record_transaction('deposit', amount=amount)
    
    def withdraw(self, amount: float) -> bool:
        """
        Withdraw funds from the account if sufficient balance is available.
        
        Args:
            amount: The amount to withdraw
            
        Returns:
            True if withdrawal was successful, False if insufficient funds or invalid amount
        """
        if amount <= 0:
            return False
        
        if self.balance >= amount:
            self.balance -= amount
            self.record_transaction('withdrawal', amount=amount)
            return True
        return False
    
    def buy_shares(self, symbol: str, quantity: int) -> bool:
        """
        Purchase shares if sufficient funds are available.
        
        Args:
            symbol: The stock symbol to buy
            quantity: The number of shares to buy
            
        Returns:
            True if purchase was successful, False if insufficient funds, invalid quantity, or unknown symbol
        """
        if quantity <= 0:
            return False
        
        share_price = get_share_price(symbol)
        if share_price == 0.0:
            return False  # Unknown symbol
        
        total_cost = share_price * quantity
        
        if self.balance >= total_cost:
            self.balance -= total_cost
            
            # Update holdings
            if symbol in self.holdings:
                self.holdings[symbol] += quantity
            else:
                self.holdings[symbol] = quantity
            
            self.record_transaction('buy', symbol=symbol, quantity=quantity, amount=total_cost)
            return True
        return False
    
    def sell_shares(self, symbol: str, quantity: int) -> bool:
        """
        Sell shares if sufficient shares are available.
        
        Args:
            symbol: The stock symbol to sell
            quantity: The number of shares to sell
            
        Returns:
            True if sale was successful, False if insufficient shares, invalid quantity, or unknown symbol
        """
        if quantity <= 0:
            return False
        
        if symbol not in self.holdings or self.holdings[symbol] < quantity:
            return False  # Insufficient shares
        
        share_price = get_share_price(symbol)
        if share_price == 0.0:
            return False  # Unknown symbol
        
        total_value = share_price * quantity
        
        self.balance += total_value
        self.holdings[symbol] -= quantity
        
        # Remove symbol if no shares left
        if self.holdings[symbol] == 0:
            del self.holdings[symbol]
        
        self.record_transaction('sell', symbol=symbol, quantity=quantity, amount=total_value)
        return True
    
    def calculate_portfolio_value(self) -> float:
        """
        Calculate the total value of the portfolio (cash balance + market value of holdings).
        
        Returns:
            The total value of the portfolio
        """
        portfolio_value = self.balance
        
        for symbol, quantity in self.holdings.items():
            share_price = get_share_price(symbol)
            portfolio_value += share_price * quantity
        
        return portfolio_value
    
    def calculate_profit_loss(self) -> float:
        """
        Calculate profit or loss based on current portfolio value vs initial deposit.
        
        Returns:
            The profit (positive) or loss (negative) amount
        """
        current_value = self.calculate_portfolio_value()
        return current_value - self.initial_deposit
    
    def report_holdings(self) -> dict:
        """
        Return a copy of the user's current holdings.
        
        Returns:
            A dictionary mapping stock symbols to quantities held
        """
        return self.holdings.copy()
    
    def report_transactions(self) -> list:
        """
        Return a list of all transaction records.
        
        Returns:
            A list of transaction dictionaries
        """
        return self.transactions.copy()
    
    def record_transaction(self, transaction_type: str, symbol: str = '', quantity: int = 0, amount: float = 0.0) -> None:
        """
        Record a transaction with necessary details.
        
        Args:
            transaction_type: The type of transaction ('deposit', 'withdrawal', 'buy', 'sell')
            symbol: The stock symbol (for buy/sell transactions)
            quantity: The number of shares (for buy/sell transactions)
            amount: The monetary amount involved in the transaction
        """
        from datetime import datetime
        
        transaction = {
            'timestamp': datetime.now().isoformat(),
            'type': transaction_type,
            'symbol': symbol,
            'quantity': quantity,
            'amount': round(amount, 2)
        }
        
        self.transactions.append(transaction)