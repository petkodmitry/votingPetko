$(document).ready(function(){
    $('#addThemeId').click(function() {
        addTheme2();
    });
});
function addTheme2() {
    var theme = $("#theme").val();
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    //var newTheme = {"theme": "theme", "startDate": "startDate", "endDate": "endDate"};
    var newTheme = {"theme": "themeSEVENjs", "startDate": "2017-01-01", "endDate": "2017-01-31"};
    $.ajax({
        headers: {"Accept":"application/json", "Content-Type":"application/json"},
        data: newTheme, dataType: "json",
        method: "POST", url: "../themes"
    }).done(function(data) {})
        /*.fail(console.log(document.headers))*/;
}
