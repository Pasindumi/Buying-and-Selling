document.getElementById('checkout-form').addEventListener('submit', function (e) {
    e.preventDefault();
    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const address = document.getElementById('address').value;
    const phone = document.getElementById('phone').value;
    const payment = document.querySelector('input[name="payment"]:checked').value;

    if (name && email && address && phone && payment) {
        alert(`Order placed successfully!`);
    } else {
        alert('Please fill all the fields.');
    }
});