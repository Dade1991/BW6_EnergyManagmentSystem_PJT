const API_BASE_URL = 'http://localhost:3001';

class ApiService {
  constructor() {
    this.baseUrl = API_BASE_URL;
  }

  getAuthToken() {
    return localStorage.getItem('authToken');
  }

  setAuthToken(token) {
    localStorage.setItem('authToken', token);
  }

  removeAuthToken() {
    localStorage.removeItem('authToken');
  }

  async request(endpoint, options = {}) {
    const url = `${this.baseUrl}${endpoint}`;
    const token = this.getAuthToken();

    const headers = {
      'Content-Type': 'application/json',
      ...options.headers,
    };

    if (token && !options.skipAuth) {
      headers['Authorization'] = `Bearer ${token}`;
    }

    const config = {
      ...options,
      headers,
    };

    try {
      const response = await fetch(url, config);

      if (!response.ok) {
        const error = await response.json().catch(() => ({
          message: 'Si è verificato un errore',
        }));
        throw new Error(error.message || `HTTP error ${response.status}`);
      }

      return await response.json();
    } catch (error) {
      console.error('API Error:', error);
      throw error;
    }
  }

  async login(email, password) {
    const response = await this.request('/auth/login', {
      method: 'POST',
      body: JSON.stringify({ email, Password: password }),
      skipAuth: true,
    });

    if (response.token) {
      this.setAuthToken(response.token);
    }

    return response;
  }

  async register(userData) {
    const response = await this.request('/auth/register', {
      method: 'POST',
      body: JSON.stringify(userData),
      skipAuth: true,
    });
    return response;
  }

  async getClienti(params = {}) {
    const queryString = new URLSearchParams(params).toString();
    const endpoint = `/clienti${queryString ? `?${queryString}` : ''}`;
    return this.request(endpoint);
  }

  async getClienteById(id) {
    return this.request(`/clienti/${id}`);
  }

  async createCliente(clienteData) {
    return this.request('/clienti', {
      method: 'POST',
      body: JSON.stringify(clienteData),
    });
  }

  async updateCliente(id, clienteData) {
    return this.request(`/clienti/${id}`, {
      method: 'PUT',
      body: JSON.stringify(clienteData),
    });
  }

  async deleteCliente(id) {
    return this.request(`/clienti/${id}`, {
      method: 'DELETE',
    });
  }

  async getFatture(params = {}) {
    const queryString = new URLSearchParams(params).toString();
    const endpoint = `/fatture${queryString ? `?${queryString}` : ''}`;
    return this.request(endpoint);
  }

  async getFatturaById(id) {
    return this.request(`/fatture/${id}`);
  }

  async createFattura(fatturaData) {
    return this.request('/fatture', {
      method: 'POST',
      body: JSON.stringify(fatturaData),
    });
  }

  async updateFattura(id, fatturaData) {
    return this.request(`/fatture/${id}`, {
      method: 'PUT',
      body: JSON.stringify(fatturaData),
    });
  }

  async deleteFattura(id) {
    return this.request(`/fatture/${id}`, {
      method: 'DELETE',
    });
  }

  async getUtenti(params = {}) {
    const queryString = new URLSearchParams(params).toString();
    const endpoint = `/utenti${queryString ? `?${queryString}` : ''}`;
    return this.request(endpoint);
  }

  async getUtenteById(id) {
    return this.request(`/utenti/${id}`);
  }

  async createUtente(utenteData) {
    return this.request('/utenti', {
      method: 'POST',
      body: JSON.stringify(utenteData),
    });
  }

  async updateUtente(id, utenteData) {
    return this.request(`/utenti/${id}`, {
      method: 'PUT',
      body: JSON.stringify(utenteData),
    });
  }

  async deleteUtente(id) {
    return this.request(`/utenti/${id}`, {
      method: 'DELETE',
    });
  }

  async getStatiFattura() {
    return this.request('/stati-fattura');
  }

  async getIndirizzi() {
    return this.request('/indirizzi');
  }

  async getRuoli() {
    return this.request('/ruoli');
  }

  logout() {
    this.removeAuthToken();
  }

  isAuthenticated() {
    return !!this.getAuthToken();
  }
}

export default new ApiService();
