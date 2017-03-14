(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('ParametroDeleteController',ParametroDeleteController);

    ParametroDeleteController.$inject = ['$uibModalInstance', 'entity', 'Parametro'];

    function ParametroDeleteController($uibModalInstance, entity, Parametro) {
        var vm = this;

        vm.parametro = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Parametro.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
