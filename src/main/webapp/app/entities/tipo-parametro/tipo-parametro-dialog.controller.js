(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('TipoParametroDialogController', TipoParametroDialogController);

    TipoParametroDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TipoParametro', 'Parametro'];

    function TipoParametroDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TipoParametro, Parametro) {
        var vm = this;

        vm.tipoParametro = entity;
        vm.clear = clear;
        vm.save = save;
        vm.parametros = Parametro.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tipoParametro.id !== null) {
                TipoParametro.update(vm.tipoParametro, onSaveSuccess, onSaveError);
            } else {
                TipoParametro.save(vm.tipoParametro, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smartTestUiApp:tipoParametroUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
