export function setPageMeta({ title, description, url, image, type = 'website' } = {}) {
  if (title) {
    document.title = title;
    setMetaProperty('og:title', title);
  }
  if (description) {
    setMetaName('description', description);
    setMetaProperty('og:description', description);
  }
  if (url) {
    setCanonical(url);
    setMetaProperty('og:url', url);
  }
  if (image) {
    setMetaProperty('og:image', image);
  }
  if (type) {
    setMetaProperty('og:type', type);
  }
}

function setMetaName(name, content) {
  let el = document.querySelector(`meta[name="${name}"]`);
  if (!el) {
    el = document.createElement('meta');
    el.setAttribute('name', name);
    document.head.appendChild(el);
  }
  el.setAttribute('content', content || '');
}

function setMetaProperty(property, content) {
  let el = document.querySelector(`meta[property="${property}"]`);
  if (!el) {
    el = document.createElement('meta');
    el.setAttribute('property', property);
    document.head.appendChild(el);
  }
  el.setAttribute('content', content || '');
}

function setCanonical(url) {
  let link = document.querySelector("link[rel='canonical']");
  if (!link) {
    link = document.createElement('link');
    link.setAttribute('rel', 'canonical');
    document.head.appendChild(link);
  }
  link.setAttribute('href', url);
}

export function setStructuredData(json) {
  if (!json) return;
  let el = document.getElementById('structured-data');
  if (!el) {
    el = document.createElement('script');
    el.type = 'application/ld+json';
    el.id = 'structured-data';
    document.head.appendChild(el);
  }
  el.text = JSON.stringify(json);
}

