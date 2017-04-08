(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('HomeQuentiController', HomeQuentiController);

    HomeQuentiController.$inject = ['pruebas', 'token', 'Prueba'];

    function HomeQuentiController (pruebas, token, Prueba) {
        var vm = this;
        vm.pruebas = pruebas;
        vm.cargarEjecucion = cargarEjecucion;
        vm.listaMetodos = [
            "GET", "POST", "PUT", "DELETE"
        ];
        vm.ejecucion = {
            metodo: "POST",
            headers: {
                token: token
            }
        };

        function cargarEjecucion(ejecucion){
            Prueba.getURI({id : ejecucion.id}).$promise.then(function (uri) {
                vm.ejecucion.url = uri;
            });

        }
    }
})();
