(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('TipoParametroDeleteController',TipoParametroDeleteController);

    TipoParametroDeleteController.$inject = ['$uibModalInstance', 'entity', 'TipoParametro'];

    function TipoParametroDeleteController($uibModalInstance, entity, TipoParametro) {
        var vm = this;

        vm.tipoParametro = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TipoParametro.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
