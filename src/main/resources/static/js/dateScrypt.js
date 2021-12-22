function dateStrict(addDays) {
    Date.prototype.addDays = function (days) {
        var date = new Date(this.valueOf());
        date.setDate(date.getDate() + days);
        return date;
    }
    var date = new Date();
    var newdate = date.addDays(addDays);
    var dd = newdate.getDate();
    var mm = newdate.getMonth() + 1; //January is 0 so need to add 1 to make it 1!
    var yyyy = newdate.getFullYear();
    if (dd < 10) {
        dd = '0' + dd
    }
    if (mm < 10) {
        mm = '0' + mm
    }
    newdate = yyyy + '-' + mm + '-' + dd;
    return newdate;
}