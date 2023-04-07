export default class MultiRef<K,V> {
  readonly map: Map<K,V>;
  ref(key: K): (value: V|null) => any;
}
