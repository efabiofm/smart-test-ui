(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('ParametroController', ParametroController);

    ParametroController.$inject = ['$scope', '$state', 'Parametro'];

    function ParametroController ($scope, $state, Parametro) {
        var vm = this;

        vm.parametros = [];

        loadAll();

        function loadAll() {
            Parametro.query(function(result) {
                vm.parametros = result;
                vm.searchQuery = null;
            });
        }
    }
})();
