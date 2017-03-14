(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('TipoParametroController', TipoParametroController);

    TipoParametroController.$inject = ['$scope', '$state', 'TipoParametro'];

    function TipoParametroController ($scope, $state, TipoParametro) {
        var vm = this;

        vm.tipoParametros = [];

        loadAll();

        function loadAll() {
            TipoParametro.query(function(result) {
                vm.tipoParametros = result;
                vm.searchQuery = null;
            });
        }
    }
})();
