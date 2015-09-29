/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$(window).load(function() {

});

$().ready(function() {
    //Init menu
    $(".menu-lout").click(function() {
        PF('dlg_logout').show();
    });
    $(".menu-home").find('a .ui-icon-home:first').click(function() {
        var rootSite = $(location).attr('protocol') + "//" + $(location).attr('host');
        window.location.href = rootSite + strContexPath;
    });
});

function outcome(id, back) {
    PrimeFaces.Mobile.navigate('#' + id, {
        reverse: back,
        transition: 'slide'
    });
}

function viewDesktopVersion() {
    $.cookie('desktop_version', '1');
    location.reload();
}