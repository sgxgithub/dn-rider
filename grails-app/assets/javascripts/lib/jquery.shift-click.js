//ref: https://forum.jquery.com/topic/jquery-1-9-1-shift-select-range-of-same-class-checkboxes-each-residing-in-same-class-divs
//ref: http://jsfiddle.net/jakecigar/QB9RT/3/

$.fn.shiftClick = function () {
    let lastSelected; // Firefox error: LastSelected is undefined
    let checkBoxes = $(this);
    this.each(function () {
        $(this).click(function (ev) {
            if (ev.shiftKey && lastSelected) {
                let last = checkBoxes.index(lastSelected);
                let first = checkBoxes.index(this);
                let start = Math.min(first, last);
                let end = Math.max(first, last);
                let chk = lastSelected.checked;
                for (let i = start; i <= end; i++) {
                    checkBoxes[i].checked = chk;
                }
            } else {
                lastSelected = this;
            }
        })
    });
};
