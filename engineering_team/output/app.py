import gradio as gr
from accounts import Account

# Global account instance for the demo
account = None

def create_account(username, initial_deposit):
    global account
    try:
        account = Account(username, float(initial_deposit))
        return f"Account created for {username} with initial deposit of ${initial_deposit}"
    except Exception as e:
        return f"Error creating account: {str(e)}"

def deposit_funds(amount):
    if account is None:
        return "Please create an account first"
    try:
        account.deposit(float(amount))
        return f"Deposited ${amount}. New balance: ${account.balance:.2f}"
    except Exception as e:
        return f"Error depositing funds: {str(e)}"

def withdraw_funds(amount):
    if account is None:
        return "Please create an account first"
    try:
        success = account.withdraw(float(amount))
        if success:
            return f"Withdrew ${amount}. New balance: ${account.balance:.2f}"
        else:
            return f"Withdrawal failed. Insufficient funds or invalid amount."
    except Exception as e:
        return f"Error withdrawing funds: {str(e)}"

def buy_shares(symbol, quantity):
    if account is None:
        return "Please create an account first"
    try:
        success = account.buy_shares(symbol, int(quantity))
        if success:
            return f"Bought {quantity} shares of {symbol}. New balance: ${account.balance:.2f}"
        else:
            return f"Purchase failed. Insufficient funds, invalid quantity, or unknown symbol."
    except Exception as e:
        return f"Error buying shares: {str(e)}"

def sell_shares(symbol, quantity):
    if account is None:
        return "Please create an account first"
    try:
        success = account.sell_shares(symbol, int(quantity))
        if success:
            return f"Sold {quantity} shares of {symbol}. New balance: ${account.balance:.2f}"
        else:
            return f"Sale failed. Insufficient shares, invalid quantity, or unknown symbol."
    except Exception as e:
        return f"Error selling shares: {str(e)}"

def get_portfolio_status():
    if account is None:
        return "Please create an account first", "N/A", "N/A"
    
    holdings = account.report_holdings()
    portfolio_value = account.calculate_portfolio_value()
    profit_loss = account.calculate_profit_loss()
    
    holdings_str = "\n".join([f"{symbol}: {qty} shares" for symbol, qty in holdings.items()]) if holdings else "No holdings"
    
    return holdings_str, f"${portfolio_value:.2f}", f"${profit_loss:.2f}"

def get_transactions():
    if account is None:
        return "Please create an account first"
    
    transactions = account.report_transactions()
    if not transactions:
        return "No transactions yet"
    
    result = []
    for tx in transactions:
        if tx['type'] in ['buy', 'sell']:
            result.append(f"{tx['timestamp'][:19]} - {tx['type'].upper()} {tx['quantity']} {tx['symbol']} (${tx['amount']:.2f})")
        else:
            result.append(f"{tx['timestamp'][:19]} - {tx['type'].upper()} ${tx['amount']:.2f}")
    
    return "\n".join(result)

# Create Gradio interface
with gr.Blocks(title="Trading Account Demo") as demo:
    gr.Markdown("# Trading Account Management System Demo")
    gr.Markdown("Available symbols: AAPL ($150), TSLA ($650), GOOGL ($2800)")
    
    with gr.Row():
        with gr.Column():
            gr.Markdown("## Account Management")
            
            # Account creation
            with gr.Row():
                username_input = gr.Textbox(label="Username", value="demo_user")
                initial_deposit_input = gr.Number(label="Initial Deposit", value=10000)
            create_btn = gr.Button("Create Account")
            create_output = gr.Textbox(label="Account Status", interactive=False)
            
            # Deposit/Withdraw
            with gr.Row():
                deposit_amount = gr.Number(label="Deposit Amount", value=1000)
                withdraw_amount = gr.Number(label="Withdraw Amount", value=500)
            
            with gr.Row():
                deposit_btn = gr.Button("Deposit")
                withdraw_btn = gr.Button("Withdraw")
            
            transaction_output = gr.Textbox(label="Transaction Result", interactive=False)
            
        with gr.Column():
            gr.Markdown("## Trading")
            
            # Buy/Sell shares
            with gr.Row():
                symbol_input = gr.Dropdown(choices=["AAPL", "TSLA", "GOOGL"], label="Symbol", value="AAPL")
                quantity_input = gr.Number(label="Quantity", value=10)
            
            with gr.Row():
                buy_btn = gr.Button("Buy Shares")
                sell_btn = gr.Button("Sell Shares")
            
            trade_output = gr.Textbox(label="Trade Result", interactive=False)
    
    gr.Markdown("## Portfolio Status")
    
    with gr.Row():
        holdings_output = gr.Textbox(label="Current Holdings", interactive=False)
        portfolio_value_output = gr.Textbox(label="Portfolio Value", interactive=False)
        profit_loss_output = gr.Textbox(label="Profit/Loss", interactive=False)
    
    status_btn = gr.Button("Refresh Portfolio Status")
    
    gr.Markdown("## Transaction History")
    transactions_output = gr.Textbox(label="Transactions", interactive=False, lines=10)
    history_btn = gr.Button("Refresh Transaction History")
    
    # Event handlers
    create_btn.click(
        create_account,
        inputs=[username_input, initial_deposit_input],
        outputs=[create_output]
    )
    
    deposit_btn.click(
        deposit_funds,
        inputs=[deposit_amount],
        outputs=[transaction_output]
    )
    
    withdraw_btn.click(
        withdraw_funds,
        inputs=[withdraw_amount],
        outputs=[transaction_output]
    )
    
    buy_btn.click(
        buy_shares,
        inputs=[symbol_input, quantity_input],
        outputs=[trade_output]
    )
    
    sell_btn.click(
        sell_shares,
        inputs=[symbol_input, quantity_input],
        outputs=[trade_output]
    )
    
    status_btn.click(
        get_portfolio_status,
        outputs=[holdings_output, portfolio_value_output, profit_loss_output]
    )
    
    history_btn.click(
        get_transactions,
        outputs=[transactions_output]
    )

if __name__ == "__main__":
    demo.launch()