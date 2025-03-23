document.getElementById('tracking-form').addEventListener('submit', function (e) {
    e.preventDefault(); 

    const orderId = document.getElementById('order-id').value;
    const email = document.getElementById('email').value;

    const orderStatus = {
        orderId: orderId,
        status: 'Out for Delivery',
    };

    updateOrderStatus(orderStatus.status);
});

function updateOrderStatus(status) {
    const steps = document.querySelectorAll('.timeline-step');
    steps.forEach(step => step.classList.remove('active'));

    if (status === 'Packing') {
        document.getElementById('step-packing').classList.add('active');
    } else if (status === 'Ready to Ship') {
        document.getElementById('step-packing').classList.add('active');
        document.getElementById('step-ready').classList.add('active');
    } else if (status === 'Out for Delivery') {
        document.getElementById('step-packing').classList.add('active');
        document.getElementById('step-ready').classList.add('active');
        document.getElementById('step-out').classList.add('active');
    } else if (status === 'Delivered') {
        steps.forEach(step => step.classList.add('active'));
    }
}