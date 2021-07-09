var planeHtmlEx = '<div class="row monthRowAdd" id="row';
var planeHtmlEx1 = '">';
planeHtmlEx1 += '<div class="monthField"><input  id="viphismonth';
var planeHtmlEx2 = '" class="date" type="text" name="viphismonth" required placeholder="Дата выплаты"><a> </a>';
planeHtmlEx2 += '<script id="funct';
var planeHtmlEx3 = '">';
planeHtmlEx3 += 'function addition';
var planeHtmlEx22 = '() { ';
planeHtmlEx22 += 'var vip_summ';
var planeHtmlEx4 = ' = parseFloat(document.getElementById(\'vip_summ';
var planeHtmlEx24 = '\').value); ';
planeHtmlEx24 += 'var nach_summ';
var planeHtmlEx5 = ' = parseFloat(document.getElementById(\'nach_summ';
var planeHtmlEx6 = '\').value); ';
planeHtmlEx6 += 'if (isNaN(vip_summ';
var planeHtmlEx7 = ')===true) vip_summ';
var planeHtmlEx8 = '=0; ';
planeHtmlEx8 += 'if (isNaN(nach_summ';
var planeHtmlEx9 = ')===true) nach_summ';
var planeHtmlEx10 = '=0; ';
planeHtmlEx10 += 'var vip_razn';
var planeHtmlEx11 = ' = vip_summ';
var planeHtmlEx12 = ' - nach_summ';
var planeHtmlEx13 = '; ';
planeHtmlEx13 += 'vip_razn';
var planeHtmlEx14 = '=vip_razn';
var planeHtmlEx15 = '.toFixed(2); ';
planeHtmlEx15 += 'document.getElementById(\'result';
var planeHtmlEx16 = '\').value = vip_razn';
var planeHtmlEx17 = '; ';
planeHtmlEx17 += '} ';
planeHtmlEx17 += '</script></div>';
planeHtmlEx17 += '<div class="monthField"><input id="vip_summ';
var planeHtmlEx18 = '" min="0" type="text"name="vip_summ" style="text-align: center; width: 200px" required placeholder="Выплаченная сумма"/><a> </a></div>';
planeHtmlEx18 += '<div class="monthField"><input id="nach_summ';
var planeHtmlEx19 = '" name="nach_summ" type="text" style="text-align: center" required placeholder="Начисленная сумма"><a> </a></div>';
planeHtmlEx19 += '<div class="monthField"><input class="button button1" type="button" value="Рассчитать" onclick="addition';
var planeHtmlEx23 = '()"><a> </a></div><div class="monthField"><input value="0" readonly style="text-align: center" id="result';
var planeHtmlEx20 = '" name="vip_razn" placeholder="Результат"/><a> </a></div>';
planeHtmlEx20 += '<div class="monthField"><button type="button" id="';
var planeHtmlEx21 = '" class="btn btn-danger btn-lg removeBtn" style="color: red"><b>X</b></div>';
planeHtmlEx21 += '</button></div>';
planeHtmlEx21 += '</div>';

$(document).ready(function () {
    var i = 1;


    $('#addBtn').on('click', function () {
        $('#fcontent').append(planeHtmlEx + i + planeHtmlEx1 + i + planeHtmlEx2 + i + planeHtmlEx3 + i + planeHtmlEx22 + i + planeHtmlEx4 + i + planeHtmlEx24 + i + planeHtmlEx5 + i + planeHtmlEx6 + i + planeHtmlEx7 + i + planeHtmlEx8 + i + planeHtmlEx9 + i + planeHtmlEx10 + i + planeHtmlEx11 + i + planeHtmlEx12 + i + planeHtmlEx13 + i + planeHtmlEx14 + i + planeHtmlEx15 + i + planeHtmlEx16 + i + planeHtmlEx17 + i + planeHtmlEx18 + i + planeHtmlEx19 + i + planeHtmlEx23 + i + planeHtmlEx20 + i + planeHtmlEx21);
        $(".date" ).datepicker();
        $.datepicker.setDefaults( $.datepicker.regional[ "ru" ] );
        i++;
    });

    $(document).on('click', '.removeBtn', function () {
        if (i > 1) {
            var btnID = $(this).attr("id");
            $('#row' + btnID + '').remove();
            i--;
        }
    });

    var viphismonth = $('input[name*="viphismonth"]');
    var arr = [];

    viphismonth.each(function () {
        if (this.value != "") {
            arr.push(this.value);
        }
    });

    var vip_summ = $('input[name*="vip_summ"]');
    arr = [];

    vip_summ.each(function () {
        if (this.value != "") {
            arr.push(this.value);
        }
    });

    var nach_summ = $('input[name*="nach_summ"]');
    arr = [];

    nach_summ.each(function () {
        if (this.value != "") {
            arr.push(this.value);
        }
    });

    var vip_razn = $('input[name*="vip_razn"]');
    arr = [];

    vip_razn.each(function () {
        if (this.value != "") {
            arr.push(this.value);
        }
    });

});