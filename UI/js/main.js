
(function ($) {
    "use strict";

    /*==================================================================
    [ Focus Contact2 ]*/
    $('.input100').each(function(){
        $(this).on('blur', function(){
            if($(this).val().trim() != "") {
                $(this).addClass('has-val');
            }
            else {
                $(this).removeClass('has-val');
            }
        })
    })


    /*==================================================================
    [ Validate after type ]*/
    $('.validate-input .input100').each(function(){
        $(this).on('blur', function(){
            if(validate(this) == false){
                showValidate(this);
            }
            else {
                $(this).parent().addClass('true-validate');
            }
        })
    })

    /*==================================================================
    [ Validate ]*/
    var input = $('.validate-input .input100');

    $('.validate-form').on('submit',function(){
        var check = true;

        var lat = document.getElementsByName("lat")[0].value;
        var long = document.getElementsByName("long")[0].value;

        for(var i=0; i<input.length; i++) {
            if(validate(input[i]) == false){
                showValidate(input[i]);
                check=false;
            }
        }
        return check;
    });


    $('.validate-form .input100').each(function(){
        $(this).focus(function(){
           hideValidate(this);
           $(this).parent().removeClass('true-validate');
        });
    });

    //$('.validate-form .contact100-form-btn').click(function() { locateControl.locate(); });
    $('.pure-button').on('click', function(){
      mymap.locate({setView: true, maxZoom: 15});
    });

    $('.show-location-btn').on('click', function(){
      var lat = document.getElementsByName("lat")[0].value;
      var long = document.getElementsByName("long")[0].value;

      L.marker([lat, long]).addTo(mymap)
				.bindPopup("<b>Hello!</b><br />This is your location.").openPopup();
       mymap.setView([lat, long], 12);
    });


    // function validate (input) {
    //     if($(input).attr('type') == 'number' || $(input).attr('name') == 'email') {
    //         if($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
    //             return false;
    //         }
    //     }
    //     else {
    //         if($(input).val().trim() == ''){
    //             return false;
    //         }
    //     }
    // }

    function showValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).addClass('alert-validate');
    }

    function hideValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).removeClass('alert-validate');
    }

})(jQuery);

  //check for location, if found show marker
  mymap.on('locationfound', onLocationFound);
  function onLocationFound(e) {
    console.log(e);
    L.marker(e.latlng).addTo(mymap)
        .bindPopup("<b>Hello!</b><br />This is your location.").openPopup();;
  }

function distanceToCityCenter() {
  var lat1 = document.getElementById("lat").value;
  var long1 = document.getElementById("long").value;
  var lat2 = document.getElementById("lat").value;
  var long2 = document.getElementById("lat").value;
  const fi1 = lat1 * Math.PI/180, fi2 = lat2 * Math.PI/180, delta_lambda = (long2-long1) * Math.PI/180, R = 6371e3;
  const d = Math.acos( Math.sin(fi1)*Math.sin(fi2) + Math.cos(fi1)*Math.cos(fi2) * Math.cos(delta_lambda) ) * R;

  return d;
}


// navigator.geolocation.getCurrentPosition(function(location) {
//     var latlng = new L.LatLng(location.coords.latitude, location.coords.longitude);
//
//     var mymap = L.map('mapid').setView(latlng, 13)
//     L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={access_token}', {
//       maxZoom: 18,
//       attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
//         '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
//         'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
//       id: 'mapbox/streets-v11',
//       tileSize: 512,
//       zoomOffset: -1,
//       access_token: 'pk.eyJ1IjoiaGkxNCIsImEiOiJjazl4bTNrbmIwMmZ0M251c2psbTBkazU2In0.Xb2Rpdxp7u-XPRinPyf_tw'
//     }).addTo(mymap);
//
//     var marker = L.marker(latlng).addTo(mymap);
// });
