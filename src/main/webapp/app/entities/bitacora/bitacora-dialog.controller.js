(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('BitacoraDialogController', BitacoraDialogController);

    BitacoraDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Bitacora', 'TipoEvento'];

    function BitacoraDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Bitacora, TipoEvento) {
        var vm = this;

        vm.bitacora = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.tipoeventos = TipoEvento.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bitacora.id !== null) {
                Bitacora.update(vm.bitacora, onSaveSuccess, onSaveError);
            } else {
                Bitacora.save(vm.bitacora, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('smartTestUiApp:bitacoraUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fecha = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
