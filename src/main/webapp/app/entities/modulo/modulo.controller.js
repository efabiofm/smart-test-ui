(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('ModuloController', ModuloController);

    ModuloController.$inject = ['$scope', '$state', 'Modulo'];

    function ModuloController ($scope, $state, Modulo) {
        var vm = this;

        vm.modulos = [];

        loadAll();

        function loadAll() {
            Modulo.query(function(result) {
                vm.modulos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
