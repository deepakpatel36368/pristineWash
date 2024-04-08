<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function() {
        $('#sendOTPBtn').click(function() {
            var username = $('#username').val();
            $.get('/sendotp/' + username, function(data, status) {
                if (status === 'success') {
                    alert('OTP sent successfully!');
                } else {
                    alert('Failed to send OTP. Please try again.');
                }
            });
        });
    });
</script>
