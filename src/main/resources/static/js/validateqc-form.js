//验证合同名
function checkcontractName(){
    let x=document.forms["htqcForm"]["contractName"].value;
    if (x==null || x=="")
    {
        alert("合同名为空");
        return false;
    }else {
        return true;
    }

}

//验证需求单位:
function checkSponsorName(){
    let x=document.forms["htqcForm"]["sponsorName"].value;
    if (x==null || x=="")
    {
        alert("需求单位为空");
        return false;
    }else {
        return true;
    }
}

//验证需求单位经办人:
function checkOperatorName(){
    let x=document.forms["htqcForm"]["operatorName"].value;
    if (x==null || x=="")
    {
        alert("需求单位经办人为空");
        return false;
    }else {
        return true;
    }
}



//验证财务预算单号:
function checkBudgetCode(){
    let x=document.forms["htqcForm"]["budgetCode"].value;
    if (x==null || x=="")
    {
        alert("财务预算单号为空");
        return false;
    }else {
        return true;
    }
}


//验证合同金额:
function checkContractMoney(){
    let x=document.forms["htqcForm"]["contractMoney"].value;
    let pattern = /^[0-9]*$/;
    let isNumber = pattern.test(x);

    if (x==null || x=="")
    {
        alert("合同金额为空");
        return false;
    }else if (!isNumber) {
        alert('金额必须为数字')
        return false;
    }else {
        return true;
    }
}

//验证合同授权代表:
function checkAuthorization(){
    let x=document.forms["htqcForm"]["authorization"].value;
    if (x==null || x=="")
    {
        alert("合同授权代表为空");
        return false;
    }else {
        return true;
    }
}


//验证份数:
function checkContractCnt(){
    let x=document.forms["htqcForm"]["contractCnt"].value;
    let pattern = /^[0-9]*$/;
    let isNumber = pattern.test(x);

    if (x==null || x=="")
    {
        alert("份数为空");
        return false;
    }else if (!isNumber) {
        alert('份数必须为数字')
        return false;
    }else {
        return true;
    }
}

//验证收件部门:
function checkDepartmentsId(){
    let x=document.forms["htqcForm"]["departmentsId"].value;
    if (x==null || x=="")
    {
        alert("收件部门为空");
        return false;
    }else {
        return true;
    }
}






$('#submitBtn').click(function (event) {

    let form = $('#submitBtn');
    if(!checkcontractName()
        || !checkSponsorName()
        || !checkOperatorName()
        || !checkContractMoney()
        || !checkAuthorization()
        || !checkBudgetCode()
        || !checkContractCnt()
        || !checkDepartmentsId()){

        event.preventDefault();
    }

    form.submit();
});
