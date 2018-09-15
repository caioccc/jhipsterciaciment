import { element, by, ElementFinder } from 'protractor';

export class VendedorComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-vendedor div table .btn-danger'));
    title = element.all(by.css('jhi-vendedor div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getText();
    }
}

export class VendedorUpdatePage {
    pageTitle = element(by.id('jhi-vendedor-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nomeInput = element(by.id('field_nome'));
    emailInput = element(by.id('field_email'));
    telefoneInput = element(by.id('field_telefone'));
    clienteSelect = element(by.id('field_cliente'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setNomeInput(nome) {
        await this.nomeInput.sendKeys(nome);
    }

    async getNomeInput() {
        return this.nomeInput.getAttribute('value');
    }

    async setEmailInput(email) {
        await this.emailInput.sendKeys(email);
    }

    async getEmailInput() {
        return this.emailInput.getAttribute('value');
    }

    async setTelefoneInput(telefone) {
        await this.telefoneInput.sendKeys(telefone);
    }

    async getTelefoneInput() {
        return this.telefoneInput.getAttribute('value');
    }

    async clienteSelectLastOption() {
        await this.clienteSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async clienteSelectOption(option) {
        await this.clienteSelect.sendKeys(option);
    }

    getClienteSelect(): ElementFinder {
        return this.clienteSelect;
    }

    async getClienteSelectedOption() {
        return this.clienteSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class VendedorDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-vendedor-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-vendedor'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
