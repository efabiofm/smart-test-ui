(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('HomeQuentiController', HomeQuentiController);

    HomeQuentiController.$inject = ['pruebas', 'token'];

    function HomeQuentiController (pruebas, token) {
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
            vm.ejecucion.body = ejecucion.body;
            vm.ejecucion.url = ejecucion.moduloNombre + "/" + ejecucion.servicioNombre + "/" + ejecucion.metodoNombre;
        }
    }
})();
