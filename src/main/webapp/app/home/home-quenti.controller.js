(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('HomeQuentiController', HomeQuentiController);

    HomeQuentiController.$inject = ['pruebas', 'token', 'Prueba',  'EjecucionPrueba'];

    function HomeQuentiController (pruebas, token, Prueba, EjecucionPrueba) {
        var vm = this;
        vm.pruebas = pruebas;
        vm.cargarEjecucion = cargarEjecucion;
        vm.ejecutarPrueba = ejecutarPrueba;
        vm.listaMetodos = [
            "GET", "POST", "PUT", "DELETE"
        ];
        vm.ejecucion = {
            metodo: "POST",
            headers: {
                token: token
            }
        };
        vm.agregarParam = agregarParam;
        vm.removerParam = removerParam;

        /*Obtiene URL a invocar para ejecutar la prueba segun el id de Prueba*/

        function cargarEjecucion(ejecucion){
            Prueba.getURI({id : ejecucion.id}).$promise.then(function (response) {
                vm.ejecucion.url = response.url;
                vm.ejecucion.params = response.parametros;
            });

        }

        function ejecutarPrueba(id) {
            EjecucionPrueba.ejecutarPrueba({id: id}).$promise.then(function (response) {
                console.log(response);
            });
        }

        function agregarParam(){
            vm.ejecucion.params.push({
                nombre: null,
                valor: null
            });
        }

        function removerParam(index){
            vm.ejecucion.params.splice(index, 1);
        }
    }
})();
