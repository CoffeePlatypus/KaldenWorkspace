var burgerVisible = true;

function toggleBurger() {
     console.log("hi")
     if(burgerVisible) {
          $('#cheezburger').hide();
          $('#buttonText').text("Give cheezburger");
     }else{
          $('#cheezburger').show();
          $('#buttonText').text("Take cheezburger :'(");
     }
     burgerVisible = !burgerVisible;
}
