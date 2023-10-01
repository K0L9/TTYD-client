import torch
import torch.nn as nn
import torch.optim as optim
import math


def main(api_request, target_db):
    x_train = torch.linspace(0, 2 * math.pi, 1000).view(-1, 1)  # Reshape to (1000, 1)
    y_train = torch.sin(x_train)

    class SimpleNN(nn.Module):
        def __init__(self):
            super(SimpleNN, self).__init__()
            self.fc1 = nn.Linear(1, 10)  # Input Layer to Hidden Layer
            self.fc2 = nn.Linear(10, 1)  # Hidden Layer to Output Layer

        def forward(self, x):
            x = torch.relu(self.fc1(x))
            x = self.fc2(x)
            return x

    # Model, Loss function and Optimizer
    model = SimpleNN()
    criterion = nn.MSELoss()
    optimizer = optim.Adam(model.parameters(), lr=0.01)

    # Training Loop
    epochs = 20
    for epoch in range(epochs):
        model.train()
        optimizer.zero_grad()
        y_pred = model(x_train)
        loss = criterion(y_pred, y_train)
        loss.backward()
        optimizer.step()

        if epoch % 10 == 0:
            print(f"Epoch {epoch}, Loss: {loss.item()}")

    # Test the model
    model.eval()
    x_test = torch.tensor([[math.pi/2]])  # sin(pi/2) = 1
    y_test = model(x_test)

    return f"sin(pi/2) ≈ {y_test.item()}\nAPI request - {api_request} - {target_db}"
