/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$(window).load(function() {
    onscroll = function() {
        var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
        if (scrollTop > 10) {
            $('#footer').slideUp("fast");

        } else {
            $('#footer').slideDown("fast");
        }
    };
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

function trimtext() {
    $(document).ready(function() {
        $(".ui-dt-c").each(function() {
            if ($.trim($(this).text()).length > iLimitTextSize) {
                $(this).attr("title", $.trim($(this).text()));
                $(this).text($.trim($(this).text()).substring(0, iLimitTextSize));
                $(this).html($(this).html() + '...');
            }
        });
    });
}

function removeUnicode(str) {
    str = str.toLowerCase();
    str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g, "a");
    str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g, "e");
    str = str.replace(/ì|í|ị|ỉ|ĩ/g, "i");
    str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g, "o");
    str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g, "u");
    str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g, "y");
    str = str.replace(/đ/g, "d");
    //                    str= str.replace(/!|@|%|\^|\*|\(|\)|\+|\=|\<|\>|\?|\/|,|\.|\:|\;|\'| |\"|\&|\#|\[|\]|~|$|_/g,"-");
    //                    str= str.replace(/-+-/g,"-"); //replace (--) to (-)
    str = str.replace(/^\-+|\-+$/g, "");
    return str;
}

function highlightAccessKeys() {
    // label
    var v_label_array = $('label[for]');//$("label");

    for (var j = 0; j < v_label_array.length; j++) {
        var v_text_lb = $(v_label_array[j]).text();

        if ($(v_label_array[j]).attr("accesskey")) {

            var v_accesskey_lb = $(v_label_array[j]).attr("accesskey");
            //                            alert(v_accesskey_lb);
            var v_tmp_lb = v_text_lb;
            v_tmp_lb = removeUnicode(v_tmp_lb);
            // check accesskey exist
            var v_index_lb = v_tmp_lb.toLowerCase().indexOf(v_accesskey_lb.toLowerCase());
            var v_first_text_lb, v_last_text_lb;

            if (v_index_lb !== -1) {
                // if exist
                $(v_label_array[j]).text("");
                v_first_text_lb = v_text_lb.substring(0, v_index_lb);
                // check space character at end of v_first_text
                if (v_first_text_lb[v_first_text_lb.length - 1] === ' ') {
                    v_first_text_lb = v_first_text_lb.substring(0, v_first_text_lb.length - 1) + "&nbsp;";
                }
                // check space character at beginning of v_last_text
                v_last_text_lb = v_text_lb.substring(v_index_lb + 1, v_text_lb.length);
                if (v_last_text_lb[0] === ' ') {
                    v_last_text_lb = '&nbsp;' + v_last_text_lb.substring(1, v_last_text_lb.length);
                }
                $(v_label_array[j]).append('<div style="float:left">' + v_first_text_lb +
                        '</div><div style="float:left" class="accesskey">' + v_text_lb.substring(v_index_lb, v_index_lb + 1) + '</div><div style="float:left">' + v_last_text_lb + '</div>');
            } else {
                // do extra
                //                                alert('zzz');
            }
        }
    }

    // button
    var v_button_arry = $("button");
    for (var i = 0; i < v_button_arry.length; i++) {
        var v_text = $(v_button_arry[i]).find("span").text();
        if ($(v_button_arry[i]).attr("accesskey")) {
            var v_accesskey = $(v_button_arry[i]).attr("accesskey");
            var v_tmp = v_text;
            v_tmp = removeUnicode(v_tmp);
            // check accesskey exist
            var v_index = v_tmp.toLowerCase().indexOf(v_accesskey.toLowerCase());
            var v_first_text, v_last_text;

            if (v_index !== -1 && $(v_button_arry[i]).hasClass("ui-button")) {
                // if exist
                $(v_button_arry[i]).find("span").remove();
                v_first_text = v_text.substring(0, v_index);
                // check space character at end of v_first_text
                if (v_first_text[v_first_text.length - 1] === ' ') {
                    v_first_text = v_first_text.substring(0, v_first_text.length - 1) + "&nbsp;";
                }
                // check space character at beginning of v_last_text
                v_last_text = v_text.substring(v_index + 1, v_text.length);
                if (v_last_text[0] === ' ') {
                    v_last_text = '&nbsp;' + v_last_text.substring(1, v_last_text.length);
                }
                $(v_button_arry[i]).append('<span class="ui-button-text" style="height:15px"><div style="text-align:center !important;float:left;" align="center"><div style="float:left;">' + v_first_text +
                        '</div><div style="float:left" class="accesskeyts">' + v_text.substring(v_index, v_index + 1) + '</div><div style="float:left">' + v_last_text + '</div><div><span>');
                $(v_button_arry[i]).find("span").find("span").remove();
            } else {
                $(v_button_arry[i]).find("span").css("float", "left");
                //                                alert($(v_button_arry[i]).find("span").text());
            }
        }
    }
}

//Fix Shift select
PrimeFaces.widget.DataTable.prototype.selectRowsInRange = function(row) {

    var rows = this.tbody.children(),
            _self = this;

    //unselect previously selected rows with shift
    if (this.cursorIndex) {
        var oldCursorIndex = this.cursorIndex,
                rowsToUnselect = oldCursorIndex > this.originRowIndex
                ? rows.slice(this.originRowIndex, oldCursorIndex + 1)
                : rows.slice(oldCursorIndex, this.originRowIndex + 1);

        rowsToUnselect.each(function(i, item) {
            _self.unselectRow($(item), true);
        });
    }

    //select rows between cursor and origin
    this.cursorIndex = row.index();

    var rowsToSelect = this.cursorIndex > this.originRowIndex
            ? rows.slice(this.originRowIndex, this.cursorIndex + 1)
            : rows.slice(this.cursorIndex, this.originRowIndex + 1);

    //var i=0;
    rowsToSelect.each(
            function(i, item) {
                if (!(i >= rowsToSelect.length - 1)) {
                    _self.selectRow($(item), true);
                } else {
                    _self.selectRow($(item), false);
                }
                i++;
            }
    );
};

function removeDialog() {
    $('div.ui-confirm-dialog').hide();
}

function handleSubmitRequest(id, xhr, status, args) {
    if (args.errorProcess || args.validationFailed)
    {
        jQuery(id).effect("shake", {
            times: 3
        }, 100);
    }
    else
    {
        $(id).find('input').each(function() {
            alert(this.type);
        });
    }
}

function fixShadow() {
    $('div.shadowfix').remove();
}

function fixRowExpand() {
    $('.ui-expanded-row-content').removeClass('ui-widget-content');
}

function showResult(id) {
    $('#' + id).hide();
    $('#' + id).fadeIn();
}

function setWidthLikeElement(sourceClass, targetClass, pxPadding) {
    var sourceWidth = $('.' + sourceClass).width();
    $('.' + targetClass).width(sourceWidth - pxPadding);
}

function maskedCalendar() {
    $(".hasDatepicker").mask("99/99/9999");
}

function checkAllCollumns(id, value) {
    //Get right check ids
    var checkIds = $("[id='form_main:table_module_right']").find(".right-chkbox").map(function() {
        return this.id;
    }).get();

    //Get class name
    var className = $("[id='" + id.replace('_input', '') + "'] span").attr('class');

    //Set right check
    for (i = 0; i < checkIds.length; i++) {
        if (id.substring(0, id.indexOf("j_idt")) === checkIds[i].substring(0, checkIds[i].indexOf("j_idt"))) {
            $("[id='" + checkIds[i] + "_input']").val(value);
            $("[id='" + checkIds[i] + "'] span").attr('class', className);
        }
    }
}

function fixIdCheckboxHeader() {
    var ids = $("[id='form_main:table_module_right']").find(".ui-dynamic-column").map(function() {
        return this.id;
    }).get();

    for (i = 0; i < ids.length; i++) {
        $("[id='" + ids[i] + "'] .check-all-row").attr('id', ids[i] + "_div");
    }
}

function initCheckBoxRight() {
    //Fix id checkbox header
    fixIdCheckboxHeader();

    //Init var
    var state = 1;
    var chkState = [];

    //Set style class
    chkState[0] = "ui-chkbox-icon";
    chkState[1] = "ui-chkbox-icon ui-icon ui-icon-circle-check";
    chkState[2] = "ui-chkbox-icon ui-icon ui-icon-radio-off";

    //Reset check collumn
    $(".check-col span").attr('class', chkState[0]);
    $(".check-col div").removeClass('ui-state-active');

    //Fix style header
    $("[id='form_main:table_module_right'] th").css("cursor", "pointer");
    $("[id='form_main:table_module_right'] .ui-chkbox-box").removeClass("ui-state-disabled");

    //Header click event
    $("[id='form_main:table_module_right'] th").click(function() {
        //Get id header
        var idCheckAll = this.id;
        //Get style class check box header
        var styleClass = $("[id='" + idCheckAll + "'] .ui-chkbox").attr("class");

        //Get all access right check box
        var ids = $("[id='form_main:table_module_right']").find(".right-chkbox").map(function() {
            return this.id;
        }).get();

        //If check all
        if (styleClass.indexOf("check-all") > 0) {
            for (i = 0; i < ids.length; i++) {
                $("[id='" + ids[i] + "_input']").val(state);
                $("[id='" + ids[i] + "'] span").attr('class', chkState[state]);
                $(".check-all span").attr('class', chkState[state]);
                $(".check-col span").attr('class', chkState[state]);
                $(".check-row span").attr('class', chkState[state]);
            }
        }
        //If check all row
        else if (styleClass.indexOf("check-row") > 0) {
            var rowIdNumber = idCheckAll.match("module_right:(.+)");

            for (i = 0; i < ids.length; i++) {
                if (ids[i].indexOf(rowIdNumber[1]) > 0) {
                    $("[id='" + ids[i] + "_input']").val(state);
                    $("[id='" + ids[i] + "'] span").attr('class', chkState[state]);
                    $("[id='" + idCheckAll + "'] .check-row span").attr('class', chkState[state]);
                }
            }
        }

        //Set state
        state = state + 1;
        if (state === 3) {
            state = 0;
        }
    });
}


function removeIndentText(id) {
    if (!$("[id='" + id.replace('_input', '') + "']").length) {
        return;
    }

    var strLabel = $("[id='" + id.replace('_input', '') + "'] .ui-selectonemenu-label").html();
    if (strLabel !== "&nbsp;") {
        $("[id='" + id.replace('_input', '') + "'] .ui-selectonemenu-label").html(strLabel.replace(/&nbsp;/g, ''));
    }
}

function maskedMoney() {
    $(".hasMoney").maskMoney({precision: 0, allowZero: true});
}

function maskedMoneyNegative() {
    $(".hasMoney").maskMoney({precision: 0, allowZero: true, allowNegative: true});
}

function maskedMoney2() {
    $(".hasMoney").maskMoney({precision: 2, allowZero: true});
}

function maskedMoney2Negative() {
    $(".hasMoney").maskMoney({precision: 2, allowZero: true, allowNegative: true});
}

function outcome(id, back) {
    PrimeFaces.Mobile.navigate('#' + id, {
        reverse: back,
        transition: 'fade'
    });
}