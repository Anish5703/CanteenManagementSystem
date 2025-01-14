window.onload = function () {
    validateForm();
};

function validateForm() {
    // Read input fields
    const uname = document.getElementById("username");
    const password = document.getElementById("password");
    const phone = document.getElementById("number"); // Correct ID for phone
    const confirmPassword = document.getElementById("confirm-password"); // Correct ID
    const submitBtn = document.querySelector("button[type='submit']");

    // Validation logic
    const fields = [uname, password, phone, confirmPassword];

    fields.forEach((field) => {
        field.addEventListener("input", () => {
            const allFilled = fields.every((f) => f && f.value.trim() !== "");
            submitBtn.disabled = !allFilled;
        });
    });
}

function handleSubmit(event) {
    // Read form elements
    const uname = document.getElementById("username");
    const password = document.getElementById("password");
    const confirmPassword = document.getElementById("confirm-password");
    const phone = document.getElementById("number");

    // Initialize error variables
    let unameErr = "";
    let passwordErr = "";
    let confirmPasswordErr = "";
    let phoneErr = "";

    // Validate username
    if (uname.value.length < 5 || uname.value.length > 20) {
        unameErr = "Name must be between 5 and 20 characters";
    } else if (!/^[A-Za-z]+( [A-Za-z]+)*$/.test(uname.value)) {
        unameErr = "Name must contain only alphabets";
    }

    // Validate password
    if (password.value.length < 8 || password.value.length > 12) {
        passwordErr = "Password must be between 8 and 12 characters";
    }

    // Validate confirm password
    if (confirmPassword.value !== password.value) {
        confirmPasswordErr = "Passwords do not match";
    }

    // Validate phone number
    const phoneRegex = /^\d{10}$/;
    if (!phoneRegex.test(phone.value)) {
        phoneErr = "Phone number must be 10 digits long";
    }

    // Display errors
    document.getElementById("usernameErr").innerText = unameErr;
    document.getElementById("passwordErr").innerText = passwordErr;
    document.getElementById("confirmPasswordErr").innerText = confirmPasswordErr;
    document.getElementById("phoneErr").innerText = phoneErr;
    console.log(unameErr,passwordErr,confirmPasswordErr,phoneErr);
    // Prevent form submission if errors exist
    if (unameErr || passwordErr || confirmPasswordErr || phoneErr) {
        event.preventDefault();
        return false;
    }

    return true;
}
