# Backend Design for Account Management System

## System Overview
The account management system is designed to handle user accounts for a trading simulation. It provides functionalities for user registration, money deposit, share purchasing, and portfolio management.

## Architecture
```
```
+-------------------
| AccountManager
+-------------------
| - balance: float
| - holdings: dict
| - transactions: list
+-------------------
| + __init__(initial_deposit: float)
| + deposit(amount: float)
| + withdraw(amount: float)
| + buy_shares(symbol: str, quantity: int)
| + sell_shares(symbol: str, quantity: int)
| + get_portfolio_value() -> float
| + get_profit_loss() -> float
| + get_holdings() -> dict
| + get_transactions() -> list
+-------------------
```

## Module Boundaries
- The module contains the `AccountManager` class which encapsulates all functionalities related to account management.

## Flows
1. **Creating an account:** User provides initial deposit to create an instance of `AccountManager`.
2. **Deposits and Withdrawals:** Users can deposit or withdraw funds which are updated in the balance.
3. **Buying and Selling Shares:** Users can buy or sell shares which alters their holdings and balance accordingly.
4. **Calculating Portfolio and Profit/Loss:** Users can query their current portfolio value and profit or loss against the initial deposit.