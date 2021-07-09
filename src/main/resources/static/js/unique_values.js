var uniques = {};
var list = $('[data-value]').filter(function () {
    var category = $(this).unique('data-value');

    if (uniques[category]) {
        return false;
    } else {
        uniques[category] = true;
        return true;
    }
}).removeClass('inactive').addClass('active');
