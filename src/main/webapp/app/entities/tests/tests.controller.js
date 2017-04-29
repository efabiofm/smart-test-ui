(function () {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('JhiTestsController', JhiTestsController);

    JhiTestsController.$inject = ['EjecucionPrueba'];

    function JhiTestsController (EjecucionPrueba) {
        // This controller uses a Websocket connection to receive user activities in real-time.
        var vm = this;
        vm.filtrar = '';

        vm.pruebas = [];

        loadEjecuciones();
        refreshEjecuciones();

        function loadEjecuciones(){
            EjecucionPrueba.query().$promise.then(function (resultado){
                vm.pruebasPendientes = [];
                vm.pruebasCompletadas = [];
                for(var index in resultado){
                    var prueba = resultado[index];
                    if(prueba.estado === "Pendiente"){
                        vm.pruebasPendientes.push(prueba);
                    }else if(prueba.estado === "Pass" || prueba.estado === "Fail"){
                        vm.pruebasCompletadas.push(prueba);
                    }
                }
            });
        }

        function refreshEjecuciones() {
            setInterval(loadEjecuciones, 1000);
        }

        vm.tests = [];

    }
})();
