document.addEventListener("DOMContentLoaded", function(event) {
    setLaungageButtonsListener()
    console.log("asd")
});

function setLaungageButtonsListener(){
    addEventListenerQuery(".language-button", "click", onLanguageButtonClick)
}

function addEventListenerQuery(query, event, fn) {
    var list = document.querySelectorAll(query);
    for (var i = 0, len = list.length; i < len; i++) {
        list[i].addEventListener(event, fn, false);//wrapper na fn, i twórz własny object Node do którego dołącz remove add class
    }
}

function onLanguageButtonClick(event){
    toggleButtonActivation(event.target)
    //TODO pokaz i ukryj tekst tak jak toggleButtonActivation
}


function toggleButtonActivation(button) {
    button.addClass("active")

    if (button.previousSibling) {
        button.previousSibling.removeClass("active")
    } else if (button.nextSibling) {
        button.nextSibling.removeClass("active")
    }
}

Object.prototype.removeClass = function(className){
    if(!this.classList){
        throw new Error("incorrect object")
    }

    this.classList.remove(className)
}

Object.prototype.addClass = function(className){
    if(!this.classList){
        throw new Error("incorrect object")
    }

    this.classList.add(className)
}