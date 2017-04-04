(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('EjecucionPruebaController', EjecucionPruebaController);

    EjecucionPruebaController.$inject = ['$scope', '$state', 'EjecucionPrueba'];

    function EjecucionPruebaController ($scope, $state, EjecucionPrueba) {
        var vm = this;

        vm.ejecucionPruebas = [];

        loadAll();

        function loadAll() {
            EjecucionPrueba.query(function(result) {
                vm.ejecucionPruebas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
