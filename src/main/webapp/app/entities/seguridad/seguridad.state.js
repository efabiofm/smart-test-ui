(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('seguridad', {
            parent: 'entity',
            url: '/seguridad',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartTestUiApp.seguridad.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/seguridad/seguridads.html',
                    controller: 'SeguridadController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('seguridad');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('seguridad-detail', {
            parent: 'entity',
            url: '/seguridad/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartTestUiApp.seguridad.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/seguridad/seguridad-detail.html',
                    controller: 'SeguridadDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('seguridad');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Seguridad', function($stateParams, Seguridad) {
                    return Seguridad.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'seguridad',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('seguridad-detail.edit', {
            parent: 'seguridad-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seguridad/seguridad-dialog.html',
                    controller: 'SeguridadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Seguridad', function(Seguridad) {
                            return Seguridad.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('seguridad.new', {
            parent: 'seguridad',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seguridad/seguridad-dialog.html',
                    controller: 'SeguridadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                token: null,
                                fecha: null,
                                jhUserId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('seguridad', null, { reload: 'seguridad' });
                }, function() {
                    $state.go('seguridad');
                });
            }]
        })
        .state('seguridad.edit', {
            parent: 'seguridad',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seguridad/seguridad-dialog.html',
                    controller: 'SeguridadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Seguridad', function(Seguridad) {
                            return Seguridad.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('seguridad', null, { reload: 'seguridad' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('seguridad.delete', {
            parent: 'seguridad',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seguridad/seguridad-delete-dialog.html',
                    controller: 'SeguridadDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Seguridad', function(Seguridad) {
                            return Seguridad.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('seguridad', null, { reload: 'seguridad' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
