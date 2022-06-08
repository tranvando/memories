<!--    Validate form-->
function Validator(options) {

    function validate(inputElement, rule) { //Hàm thực hiện validate

        var errorMessage;
        var errorElement = inputElement.parentElement.querySelector(options.errorSelector);

        //lấy ra các rule của selector
        var rules = selectorRules[rule.selector];

        //lặp qua từng rule vả kiểm tra
        //Nếu có lỗi thì dừng việc kiểm tra
        for(var i=0; i<rules.length; i++) {
            errorMessage = rules[i](inputElement.value);
            if(errorMessage) break;
        }
        if(errorMessage) {
            errorElement.innerText=errorMessage;
            inputElement.parentElement.classList.add('invalid');
        }
        else {
            inputElement.parentElement.classList.remove('invalid');
            errorElement.innerText='';
        }
        return !errorMessage;
    }

    var selectorRules = {};
    //lấy element của form cần validate
    var formElement = document.querySelector(options.form);
    if(formElement)
    {
        formElement.onsubmit = function(e){
            e.preventDefault();//bỏ hành vì submit mặc định của form
            var isFormVlaid = true;
            options.rules.forEach(function (rule) {
                var inputElement = formElement.querySelector(rule.selector);
                var isVlaid = validate(inputElement,rule);
                if(!isVlaid) {
                    isFormVlaid = false;
                }
            });

            if(isFormVlaid) {
                if(typeof options.onSubmit==='function'){

                    var enableInputs = formElement.querySelectorAll('[name]:not([disabled])')//nodelist
                    var formValues = Array.from(enableInputs).reduce(function (value, input) {
                        return (value[input.name]=input.value) && value;
                    },{})//convert => array
                    options.onSubmit(formValues);
                }
                else{
                    formElement.submit();
                }
            }
        }



        //lặp qua mỗi rule
        options.rules.forEach(function (rule) {
            //lưu lại các rules cho mỗi input
            if(Array.isArray(selectorRules[rule.selector])){
                selectorRules[rule.selector].push(rule.test);
            }
            else{
                selectorRules[rule.selector] = [rule.test];
            }

            var inputElement = formElement.querySelector(rule.selector);

            if(inputElement) {
                //xử lý trường hợp kích chuột ra khỏi input
                inputElement.onblur = function() {
                    validate(inputElement,rule);
                }
                //xử lý mỗi khi nhập vào input
                inputElement.oninput = function() {
                    var errorElement = inputElement.parentElement.querySelector('.form-message');
                    errorElement.innerText='';
                    inputElement.parentElement.classList.remove('invalid');

                }
            }
        });
    }

}

//Định nghĩa rules
//Khi có lỗi trả về thông báo lỗi
//Khi không có lỗi trả về undefined
Validator.isRequired = function(selector, message) {
    return {
        selector: selector,
        test: function(value) {
            return value.trim() ? undefined : message||'Vui lòng nhập trường này';
        }
    };
}
Validator.isEmail = function(selector,message) {
    return {
        selector: selector,
        test: function(value) {
            var regex = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return regex.test(value) ? undefined : message||'Địa chỉ email không hợp lệ';
        }
    };
}
Validator.minLength = function(selector,min,message) {
    return {
        selector: selector,
        test: function(value) {
            return value.length>=min ? undefined : message||`Vui lòng nhập tối thiểu ${min} ký tự`;
        }
    };
}
Validator.isConfirm = function(selector,getConfirmValue,message) {
    return {
        selector: selector,
        test: function(value) {
            return value===getConfirmValue() ? undefined : message||'Mật khẩu không trùng khớp';
        }
    };
}
Validator.isUsername = function(selector,message) {
    return {
        selector: selector,
        test: function(value) {
            var regex = /^[a-z0-9]{4,}$/;
            return regex.test(value) ? undefined : message||'Tên đăng nhập chứa ít nhất 4 ký tự và không chứa ký tự đặc biệt và in hoa';
        }
    };
}
Validator.isPhone = function(selector,message) {
    return {
        selector: selector,
        test: function(value) {
            var regex = /(((\+|)84)|0)(3|5|7|8|9)+([0-9]{8})\b/;
            return regex.test(value) ? undefined : message||'Số điện thoại không hợp lệ';
        }
    };
}

Validator.isImage = function(selector,fileName,message) {
    return {
        selector: selector,
        test: function(value) {
            return (fileName.value==''&&value.trim()=='') ? message||'Vui lòng chọn ảnh': undefined
        }
    };
}

Validator.isCheckBox = function(selector,checkBox,message) {
    return {
        selector: selector,
        test: function(value) {
            return value>0 ? undefined : message||'Vui lòng chọn quyền';
        }
    };
}

